"""Chat endpoint with SSE streaming."""

import json

from fastapi import APIRouter, Request
from fastapi.responses import StreamingResponse
from pydantic import BaseModel, Field

from app.ai.orchestrator import process_message
from app.api.routes.stats import increment_questions_asked
from app.core.logging import get_logger

logger = get_logger(__name__)

router = APIRouter(prefix="/api/chat", tags=["chat"])


class ChatRequest(BaseModel):
    message: str = Field(..., min_length=1, max_length=2000)
    session_id: str | None = None


@router.post("")
async def chat(request: Request, body: ChatRequest):
    """Stream a chat response via Server-Sent Events."""

    async def event_stream():
        has_content = False
        async for event in process_message(
            message=body.message,
            session_id=body.session_id,
        ):
            event_type = event.get("event", "message")
            data = event.get("data", "")
            if data and isinstance(data, dict) and data.get("text"):
                has_content = True
            yield f"event: {event_type}\ndata: {json.dumps(data)}\n\n"

        # Increment global counter after successful response
        if has_content:
            new_count = await increment_questions_asked()
            yield f"data: {json.dumps({'questions_asked': new_count})}\n\n"

    return StreamingResponse(
        event_stream(),
        media_type="text/event-stream",
        headers={
            "Cache-Control": "no-cache",
            "Connection": "keep-alive",
            "X-Accel-Buffering": "no",
        },
    )
