"""Prometheus-style metrics endpoint."""

import time

from fastapi import APIRouter
from fastapi.responses import PlainTextResponse

from app.core.logging import get_logger

logger = get_logger(__name__)

router = APIRouter(tags=["metrics"])

# Simple in-memory metrics
_metrics: dict[str, float] = {
    "requests_total": 0,
    "chat_requests_total": 0,
    "errors_total": 0,
    "start_time": time.time(),
}


def increment(metric: str, value: float = 1.0) -> None:
    """Increment a metric counter."""
    _metrics[metric] = _metrics.get(metric, 0) + value


@router.get("/metrics")
async def get_metrics():
    """Return metrics in Prometheus text format."""
    uptime = time.time() - _metrics["start_time"]

    lines = [
        "# HELP app_uptime_seconds Application uptime in seconds",
        "# TYPE app_uptime_seconds gauge",
        f"app_uptime_seconds {uptime:.1f}",
        "",
        "# HELP app_requests_total Total requests",
        "# TYPE app_requests_total counter",
        f'app_requests_total {_metrics.get("requests_total", 0):.0f}',
        "",
        "# HELP app_chat_requests_total Total chat requests",
        "# TYPE app_chat_requests_total counter",
        f'app_chat_requests_total {_metrics.get("chat_requests_total", 0):.0f}',
        "",
        "# HELP app_errors_total Total errors",
        "# TYPE app_errors_total counter",
        f'app_errors_total {_metrics.get("errors_total", 0):.0f}',
    ]

    return PlainTextResponse("\n".join(lines), media_type="text/plain")
