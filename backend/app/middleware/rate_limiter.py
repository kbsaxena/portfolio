"""Token bucket rate limiter middleware."""

import time
from dataclasses import dataclass, field

from starlette.middleware.base import BaseHTTPMiddleware, RequestResponseEndpoint
from starlette.requests import Request
from starlette.responses import JSONResponse, Response

from app.config import settings
from app.core.logging import get_logger

logger = get_logger(__name__)


@dataclass
class TokenBucket:
    """Token bucket for rate limiting."""

    capacity: float
    refill_rate: float
    tokens: float = field(init=False)
    last_refill: float = field(init=False)

    def __post_init__(self):
        self.tokens = self.capacity
        self.last_refill = time.time()

    def consume(self, tokens: float = 1.0) -> bool:
        """Try to consume tokens. Returns True if successful."""
        now = time.time()
        elapsed = now - self.last_refill
        self.tokens = min(self.capacity, self.tokens + elapsed * self.refill_rate)
        self.last_refill = now

        if self.tokens >= tokens:
            self.tokens -= tokens
            return True
        return False


@dataclass
class IPRecord:
    """Track rate limiting state per IP."""

    chat_bucket: TokenBucket
    contact_bucket: TokenBucket
    violations: int = 0
    blocked_until: float = 0.0


class RateLimitMiddleware(BaseHTTPMiddleware):
    """Rate limiting middleware with auto-blocking."""

    _SKIP_PATHS = {"/api/health", "/metrics"}
    _BLOCK_DURATION = 900  # 15 minutes
    _MAX_VIOLATIONS = 3

    def __init__(self, app):
        super().__init__(app)
        self._records: dict[str, IPRecord] = {}

    def _get_record(self, ip: str) -> IPRecord:
        if ip not in self._records:
            self._records[ip] = IPRecord(
                chat_bucket=TokenBucket(
                    capacity=settings.rate_limit_chat,
                    refill_rate=settings.rate_limit_chat / 60.0,
                ),
                contact_bucket=TokenBucket(
                    capacity=settings.rate_limit_contact,
                    refill_rate=settings.rate_limit_contact / 3600.0,
                ),
            )
        return self._records[ip]

    async def dispatch(
        self, request: Request, call_next: RequestResponseEndpoint
    ) -> Response:
        if request.url.path in self._SKIP_PATHS:
            return await call_next(request)

        ip = request.client.host if request.client else "unknown"
        record = self._get_record(ip)

        # Check if IP is blocked
        if record.blocked_until > time.time():
            return JSONResponse(
                status_code=403,
                content={
                    "error": "IP_BLOCKED",
                    "message": "Your IP has been temporarily blocked",
                },
            )

        # Determine which bucket to use
        path = request.url.path
        if "/chat" in path:
            bucket = record.chat_bucket
        elif "/contact" in path:
            bucket = record.contact_bucket
        else:
            return await call_next(request)

        if not bucket.consume():
            record.violations += 1
            logger.warning(
                f"Rate limit exceeded for {ip}, violation #{record.violations}"
            )

            if record.violations >= self._MAX_VIOLATIONS:
                record.blocked_until = time.time() + self._BLOCK_DURATION
                logger.warning(f"IP {ip} blocked for {self._BLOCK_DURATION}s")

            return JSONResponse(
                status_code=429,
                content={
                    "error": "RATE_LIMIT_EXCEEDED",
                    "message": "Too many requests, please try again later",
                },
            )

        return await call_next(request)
