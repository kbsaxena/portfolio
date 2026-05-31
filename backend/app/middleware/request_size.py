"""Request size limiting middleware."""

from starlette.middleware.base import BaseHTTPMiddleware, RequestResponseEndpoint
from starlette.requests import Request
from starlette.responses import JSONResponse, Response

from app.config import settings


class RequestSizeMiddleware(BaseHTTPMiddleware):
    """Reject requests with bodies exceeding the configured limit."""

    async def dispatch(
        self, request: Request, call_next: RequestResponseEndpoint
    ) -> Response:
        content_length = request.headers.get("content-length")

        if content_length and int(content_length) > settings.max_request_size:
            return JSONResponse(
                status_code=413,
                content={
                    "error": "PAYLOAD_TOO_LARGE",
                    "message": f"Request body exceeds {settings.max_request_size} bytes",
                },
            )

        return await call_next(request)
