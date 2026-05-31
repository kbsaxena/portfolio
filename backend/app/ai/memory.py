"""Session-based conversation memory."""

import time
from dataclasses import dataclass, field

from app.config import settings
from app.core.logging import get_logger

logger = get_logger(__name__)


@dataclass
class ConversationSession:
    """A single conversation session."""

    messages: list[dict[str, str]] = field(default_factory=list)
    last_access: float = field(default_factory=time.time)

    def add_message(self, role: str, content: str) -> None:
        """Add a message to the session."""
        self.messages.append({"role": role, "content": content})
        self.last_access = time.time()

        # Trim to max turns (each turn = user + assistant)
        max_messages = settings.max_conversation_turns * 2
        if len(self.messages) > max_messages:
            self.messages = self.messages[-max_messages:]

    def get_history(self) -> list[dict[str, str]]:
        """Get conversation history."""
        self.last_access = time.time()
        return self.messages.copy()

    def is_expired(self) -> bool:
        """Check if session has expired."""
        ttl_seconds = settings.session_ttl_minutes * 60
        return (time.time() - self.last_access) > ttl_seconds


class SessionMemory:
    """Manages multiple conversation sessions."""

    def __init__(self):
        self._sessions: dict[str, ConversationSession] = {}

    def get_or_create(self, session_id: str) -> ConversationSession:
        """Get existing session or create a new one."""
        self._evict_expired()

        if session_id not in self._sessions:
            if len(self._sessions) >= settings.max_sessions:
                self._evict_oldest()
            self._sessions[session_id] = ConversationSession()

        return self._sessions[session_id]

    def add_message(self, session_id: str, role: str, content: str) -> None:
        """Add a message to a session."""
        session = self.get_or_create(session_id)
        session.add_message(role, content)

    def get_history(self, session_id: str) -> list[dict[str, str]]:
        """Get conversation history for a session."""
        session = self.get_or_create(session_id)
        return session.get_history()

    def _evict_expired(self) -> None:
        """Remove expired sessions."""
        expired = [
            sid for sid, session in self._sessions.items() if session.is_expired()
        ]
        for sid in expired:
            del self._sessions[sid]
        if expired:
            logger.debug(f"Evicted {len(expired)} expired sessions")

    def _evict_oldest(self) -> None:
        """Remove the oldest session to make room."""
        if not self._sessions:
            return
        oldest_id = min(
            self._sessions, key=lambda k: self._sessions[k].last_access
        )
        del self._sessions[oldest_id]
        logger.debug(f"Evicted oldest session: {oldest_id}")


session_memory = SessionMemory()
