"""Global stats — persistent counters using SQLite (built-in, no external DB)."""

import hashlib
import sqlite3
import threading
from pathlib import Path

from fastapi import APIRouter, Request
from pydantic import BaseModel

from app.core.logging import get_logger

logger = get_logger(__name__)

router = APIRouter(prefix="/api/stats", tags=["stats"])

DB_PATH = Path(__file__).parent.parent.parent.parent / "data" / "stats.db"
_lock = threading.Lock()


def _get_connection() -> sqlite3.Connection:
    """Get a SQLite connection, creating the DB and tables if needed."""
    DB_PATH.parent.mkdir(parents=True, exist_ok=True)
    conn = sqlite3.connect(str(DB_PATH), timeout=5)
    conn.execute("PRAGMA journal_mode=WAL")
    conn.execute(
        """
        CREATE TABLE IF NOT EXISTS counters (
            key TEXT PRIMARY KEY,
            value INTEGER NOT NULL DEFAULT 0
        )
        """
    )
    conn.execute(
        """
        CREATE TABLE IF NOT EXISTS visitors (
            ip_hash TEXT PRIMARY KEY,
            first_visit TEXT NOT NULL DEFAULT (datetime('now'))
        )
        """
    )
    conn.commit()
    return conn


def _get_counter(key: str) -> int:
    """Read a counter value."""
    with _lock:
        conn = _get_connection()
        try:
            row = conn.execute(
                "SELECT value FROM counters WHERE key = ?", (key,)
            ).fetchone()
            return row[0] if row else 0
        finally:
            conn.close()


def _increment_counter(key: str) -> int:
    """Atomically increment a counter and return the new value."""
    with _lock:
        conn = _get_connection()
        try:
            conn.execute(
                """
                INSERT INTO counters (key, value) VALUES (?, 1)
                ON CONFLICT(key) DO UPDATE SET value = value + 1
                """,
                (key,),
            )
            conn.commit()
            row = conn.execute(
                "SELECT value FROM counters WHERE key = ?", (key,)
            ).fetchone()
            return row[0] if row else 1
        finally:
            conn.close()


def _record_visitor(ip_hash: str) -> int:
    """Record a unique visitor (by hashed IP) and return total visitor count."""
    with _lock:
        conn = _get_connection()
        try:
            conn.execute(
                """
                INSERT OR IGNORE INTO visitors (ip_hash) VALUES (?)
                """,
                (ip_hash,),
            )
            conn.commit()
            row = conn.execute("SELECT COUNT(*) FROM visitors").fetchone()
            return row[0] if row else 0
        finally:
            conn.close()


def _get_visitor_count() -> int:
    """Get total unique visitor count."""
    with _lock:
        conn = _get_connection()
        try:
            row = conn.execute("SELECT COUNT(*) FROM visitors").fetchone()
            return row[0] if row else 0
        finally:
            conn.close()


class StatsResponse(BaseModel):
    questions_asked: int
    visitors: int


@router.get("", response_model=StatsResponse)
async def get_stats(request: Request):
    """Return global stats. Also records the visitor."""
    ip = request.client.host if request.client else "unknown"
    ip_hash = hashlib.sha256(ip.encode()).hexdigest()
    visitors = _record_visitor(ip_hash)
    questions = _get_counter("questions_asked")
    return StatsResponse(questions_asked=questions, visitors=visitors)


async def increment_questions_asked() -> int:
    """Increment the global questions counter and return the new value."""
    return _increment_counter("questions_asked")
