"""Input/output guardrails for the AI pipeline."""

import re

import bleach

from app.core.logging import get_logger

logger = get_logger(__name__)

_INJECTION_PATTERNS: list[re.Pattern] = [
    re.compile(r"ignore\s+(all\s+)?previous\s+instructions", re.IGNORECASE),
    re.compile(r"you\s+are\s+now\s+a", re.IGNORECASE),
    re.compile(r"system\s*:\s*", re.IGNORECASE),
    re.compile(r"<\|im_start\|>", re.IGNORECASE),
    re.compile(r"\[INST\]", re.IGNORECASE),
    re.compile(r"forget\s+(everything|all)", re.IGNORECASE),
    re.compile(r"new\s+instructions?\s*:", re.IGNORECASE),
    re.compile(r"override\s+(system|prompt)", re.IGNORECASE),
    re.compile(r"act\s+as\s+(if\s+)?you", re.IGNORECASE),
    re.compile(r"pretend\s+(you\s+are|to\s+be)", re.IGNORECASE),
]

_SYSTEM_PROMPT_MARKERS: list[str] = [
    "<<SYS>>",
    "<|system|>",
    "[SYSTEM]",
    "### System:",
    "SYSTEM PROMPT:",
]


def sanitize_input(text: str) -> str:
    """Clean and truncate user input."""
    # Strip HTML
    text = bleach.clean(text, tags=[], strip=True)
    # Truncate to 2000 chars
    text = text[:2000]
    # Normalize whitespace
    text = " ".join(text.split())
    return text.strip()


def detect_injection(text: str) -> bool:
    """Check if text contains prompt injection patterns."""
    for pattern in _INJECTION_PATTERNS:
        if pattern.search(text):
            logger.warning(f"Injection pattern detected: {pattern.pattern}")
            return True
    return False


def sanitize_output(text: str) -> str:
    """Strip system prompt fragments from output."""
    for marker in _SYSTEM_PROMPT_MARKERS:
        text = text.replace(marker, "")
    # Remove anything that looks like a system instruction leak
    text = re.sub(
        r"(You are an AI assistant|Your instructions are).*?(\.|$)",
        "",
        text,
        flags=re.IGNORECASE,
    )
    return text.strip()
