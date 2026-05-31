"""Groq LLM client with circuit breaker and retry logic."""

import asyncio
from collections.abc import AsyncGenerator

from groq import AsyncGroq, RateLimitError

from app.config import settings
from app.core.circuit_breaker import CircuitBreaker
from app.core.exceptions import ServiceUnavailableError
from app.core.logging import get_logger

logger = get_logger(__name__)

# Module-level circuit breaker
_circuit_breaker = CircuitBreaker(
    threshold=settings.circuit_breaker_threshold,
    timeout=settings.circuit_breaker_timeout,
)


class GroqClient:
    """Async Groq LLM client."""

    def __init__(self):
        self._client = AsyncGroq(api_key=settings.groq_api_key)

    async def generate(
        self,
        messages: list[dict[str, str]],
        max_tokens: int | None = None,
        temperature: float = 0.7,
    ) -> str:
        """Generate a non-streaming response."""
        if _circuit_breaker.is_open:
            raise ServiceUnavailableError("LLM service circuit breaker is open")

        max_tokens = max_tokens or settings.groq_max_tokens

        for attempt in range(3):
            try:
                response = await self._client.chat.completions.create(
                    model=settings.groq_model,
                    messages=messages,
                    max_tokens=max_tokens,
                    temperature=temperature,
                )
                _circuit_breaker.record_success()
                return response.choices[0].message.content or ""
            except RateLimitError:
                wait_time = 2**attempt
                logger.warning(
                    f"Rate limited by Groq, retrying in {wait_time}s "
                    f"(attempt {attempt + 1}/3)"
                )
                await asyncio.sleep(wait_time)
            except Exception as e:
                _circuit_breaker.record_failure()
                logger.error(f"Groq API error: {e}")
                raise ServiceUnavailableError(f"LLM service error: {e}")

        _circuit_breaker.record_failure()
        raise ServiceUnavailableError("LLM rate limit exceeded after retries")

    async def stream(
        self,
        messages: list[dict[str, str]],
        max_tokens: int | None = None,
        temperature: float = 0.7,
    ) -> AsyncGenerator[str, None]:
        """Generate a streaming response."""
        if _circuit_breaker.is_open:
            raise ServiceUnavailableError("LLM service circuit breaker is open")

        max_tokens = max_tokens or settings.groq_max_tokens

        for attempt in range(3):
            try:
                stream = await self._client.chat.completions.create(
                    model=settings.groq_model,
                    messages=messages,
                    max_tokens=max_tokens,
                    temperature=temperature,
                    stream=True,
                )
                async for chunk in stream:
                    if chunk.choices[0].delta.content:
                        yield chunk.choices[0].delta.content
                _circuit_breaker.record_success()
                return
            except RateLimitError:
                wait_time = 2**attempt
                logger.warning(
                    f"Rate limited by Groq (stream), retrying in {wait_time}s "
                    f"(attempt {attempt + 1}/3)"
                )
                await asyncio.sleep(wait_time)
            except Exception as e:
                _circuit_breaker.record_failure()
                logger.error(f"Groq streaming error: {e}")
                raise ServiceUnavailableError(f"LLM streaming error: {e}")

        _circuit_breaker.record_failure()
        raise ServiceUnavailableError("LLM rate limit exceeded after retries")


groq_client = GroqClient()
