"""Global stats endpoint — persistent questions-answered counter."""

import asyncio
from pathlib import Path

from fastapi import APIRouter
from pydantic import BaseModel

from app.core.logging import get_logger

logger = get_logger(__name__)

router = APIRouter(prefix="/api/stats", tags=["stats"])

COUNTER_FILE = Path(__file__).parent.parent.parent.parent / "data" / "counter.txt"
_lock = asyncio.Lock()


def _read_counter() -> int:
    """Read the counter from disk."""
    try:
        if COUNTER_FILE.exists():
            return int(COUNTER_FILE.read_text().strip())
    except (ValueError, OSError) as e:
        logger.warning(f"Failed to read counter file: {e}")
    return 47  # default starting value


def _write_counter(value: int) -> None:
    """Write the counter to disk."""
    try:
        COUNTER_FILE.parent.mkdir(parents=True, exist_ok=True)
        COUNTER_FILE.write_text(str(value))
    except OSError as e:
        logger.error(f"Failed to write counter file: {e}")


class StatsResponse(BaseModel):
    questions_answered: int


@router.get("", response_model=StatsResponse)
async def get_stats():
    """Return the global questions-answered count."""
    return StatsResponse(questions_answered=_read_counter())


async def increment_questions_answered() -> int:
    """Increment the global counter and return the new value."""
    async with _lock:
        current = _read_counter()
        current += 1
        _write_counter(current)
        return current
