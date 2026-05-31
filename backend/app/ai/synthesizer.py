"""Response synthesis with streaming."""

from collections.abc import AsyncGenerator

from app.ai.llm import groq_client
from app.core.logging import get_logger

logger = get_logger(__name__)

_SYSTEM_PROMPT = """You are Kulbhushan Saxena's portfolio AI assistant. You help visitors learn about his professional background, skills, projects, and experience.

Guidelines:
- Answer naturally and conversationally, as if introducing Kulbhushan to someone
- ALWAYS use markdown formatting: **bold** for key terms, bullet points for lists, headings for sections
- Use the provided context to give accurate, specific answers
- If you have conversation history, use it for continuity
- Never mention file names, brackets, or internal references
- If you don't have enough information, say so honestly
- Keep responses concise but well-structured (use bullet points, not walls of text)
- Be friendly and professional
- You DO remember the current conversation — refer back to earlier messages naturally"""


async def synthesize_response(
    query: str,
    tool_results: list[dict],
    conversation_history: list[dict[str, str]],
    category: str,
) -> AsyncGenerator[str, None]:
    """Synthesize a streaming response from tool results and context."""
    # Build context from tool results
    context_parts: list[str] = []
    citations: list[dict] = []

    for result in tool_results:
        if result.get("content"):
            # Check confidence threshold (skip for general)
            if category != "general" and result.get("confidence", 1.0) < 0.2:
                continue

            label = "Context about Kulbhushan"
            if result.get("source") == "web_search":
                label = "Web search results"

            context_parts.append(f"{label}:\n{result['content']}")

            if result.get("citations"):
                citations.extend(result["citations"])

    # Build messages
    messages: list[dict[str, str]] = [{"role": "system", "content": _SYSTEM_PROMPT}]

    # Add conversation history
    messages.extend(conversation_history)

    # Build user message with context
    if context_parts:
        context_text = "\n\n".join(context_parts)
        user_message = (
            f"{context_text}\n\n"
            f"Answer naturally and conversationally.\n\n"
            f"Question: {query}"
        )
    else:
        user_message = query

    messages.append({"role": "user", "content": user_message})

    # Stream response
    async for token in groq_client.stream(messages=messages):
        yield token
