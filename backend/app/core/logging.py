"""Structured JSON logging with PII sanitization."""

import json
import logging
import re
from datetime import datetime, timezone


class PIISanitizingFilter(logging.Filter):
    """Filter that redacts PII from log records."""

    _EMAIL_PATTERN = re.compile(r"[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}")
    _PHONE_PATTERN = re.compile(r"\b\d{3}[-.]?\d{3}[-.]?\d{4}\b")
    _IP_PATTERN = re.compile(r"\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b")

    def filter(self, record: logging.LogRecord) -> bool:
        if isinstance(record.msg, str):
            record.msg = self._sanitize(record.msg)
        return True

    def _sanitize(self, text: str) -> str:
        text = self._EMAIL_PATTERN.sub("[REDACTED_EMAIL]", text)
        text = self._PHONE_PATTERN.sub("[REDACTED_PHONE]", text)
        text = self._IP_PATTERN.sub("[REDACTED_IP]", text)
        return text


class CustomJsonFormatter(logging.Formatter):
    """JSON log formatter."""

    def format(self, record: logging.LogRecord) -> str:
        log_entry = {
            "timestamp": datetime.now(timezone.utc).isoformat(),
            "level": record.levelname,
            "logger": record.name,
            "message": record.getMessage(),
            "module": record.module,
            "function": record.funcName,
            "line": record.lineno,
        }
        if record.exc_info:
            log_entry["exception"] = self.formatException(record.exc_info)
        if hasattr(record, "request_id"):
            log_entry["request_id"] = record.request_id
        return json.dumps(log_entry)


def setup_logging(level: str = "INFO", use_json: bool = True) -> None:
    """Configure application logging."""
    root_logger = logging.getLogger()
    root_logger.setLevel(getattr(logging, level.upper(), logging.INFO))

    # Clear existing handlers
    root_logger.handlers.clear()

    handler = logging.StreamHandler()
    if use_json:
        handler.setFormatter(CustomJsonFormatter())
    else:
        handler.setFormatter(
            logging.Formatter(
                "%(asctime)s - %(name)s - %(levelname)s - %(message)s"
            )
        )

    handler.addFilter(PIISanitizingFilter())
    root_logger.addHandler(handler)

    # Suppress noisy loggers
    logging.getLogger("uvicorn.access").setLevel(logging.WARNING)
    logging.getLogger("httpx").setLevel(logging.WARNING)


def get_logger(name: str) -> logging.Logger:
    """Get a named logger."""
    return logging.getLogger(name)
