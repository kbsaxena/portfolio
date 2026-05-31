"""Main AI pipeline orchestrator."""

import asyncio
import json
import uuid
from collections.abc import AsyncGenerator

from app.ai.classifier import classify_query
from app.ai.guardrails import detect_injection, sanitize_input, sanitize_output
from app.ai.memory import session_memory
from app.ai.synthesizer import synthesize_response
from app.ai.tools.registry import tool_registry
from app.core.logging import get_logger

logger = get_logger(__name__)


async def process_message(
    message: str, session_id: str | None = None
) -> AsyncGenerator[dict, None]:
    """Process a user message through the AI pipeline.

    Yields SSE events: status, token, citation, done, error
    """
    session_id = session_id or str(uuid.uuid4())

    try:
        # Step 1: Sanitize input
        sanitized = sanitize_input(message)
        if not sanitized:
            yield {"event": "error", "data": {"message": "Empty message"}}
            return

        # Step 2: Check for injection
        if detect_injection(sanitized):
            yield {"event": "token", "data": {"text": "I can only help with questions about the portfolio. How can I assist you?"}}
            yield {"event": "done", "data": {"session_id": session_id}}
            return

        # Step 3: Classify query
        yield {"event": "status", "data": {"stage": "understanding"}}
        classification = await classify_query(sanitized)
        category = classification["category"]

        # Step 4: Execute tools
        yield {"event": "status", "data": {"stage": "searching"}}
        tool_results = await _execute_tools(category, sanitized)

        # Step 5: Get conversation history
        history = session_memory.get_history(session_id)

        # Step 6: Synthesize and stream response
        yield {"event": "status", "data": {"stage": "generating"}}
        full_response = ""

        async for token in synthesize_response(
            query=sanitized,
            tool_results=tool_results,
            conversation_history=history,
            category=category,
        ):
            clean_token = sanitize_output(token)
            if clean_token:
                full_response += clean_token
                yield {"event": "token", "data": {"text": clean_token}}

        # Step 7: Emit citations
        for result in tool_results:
            if result.get("citations"):
                for citation in result["citations"]:
                    yield {"event": "citation", "data": json.dumps(citation)}

        # Step 8: Save to memory
        session_memory.add_message(session_id, "user", sanitized)
        session_memory.add_message(session_id, "assistant", full_response)

        yield {"event": "done", "data": {"session_id": session_id}}

    except Exception as e:
        logger.error(f"Pipeline error: {e}", exc_info=True)
        yield {"event": "token", "data": {"text": "Something went wrong. Please try again."}}
        yield {"event": "done", "data": {"session_id": session_id}}


async def _execute_tools(category: str, query: str) -> list[dict]:
    """Execute appropriate tools based on category."""
    results: list[dict] = []

    if category == "rag":
        rag_tool = tool_registry.get("rag_search")
        if rag_tool:
            result = await rag_tool.execute(query=query)
            results.append(result.to_dict())

    elif category == "web_search":
        web_tool = tool_registry.get("web_search")
        if web_tool:
            result = await web_tool.execute(query=query)
            results.append(result.to_dict())

    elif category == "rag+web":
        # Execute both in parallel
        tasks = []
        rag_tool = tool_registry.get("rag_search")
        web_tool = tool_registry.get("web_search")

        if rag_tool:
            tasks.append(rag_tool.execute(query=query))
        if web_tool:
            tasks.append(web_tool.execute(query=query))

        if tasks:
            completed = await asyncio.gather(*tasks, return_exceptions=True)
            for result in completed:
                if isinstance(result, Exception):
                    logger.warning(f"Tool execution failed: {result}")
                else:
                    results.append(result.to_dict())

    elif category == "general":
        general_tool = tool_registry.get("general")
        if general_tool:
            result = await general_tool.execute(query=query)
            results.append(result.to_dict())

    return results
