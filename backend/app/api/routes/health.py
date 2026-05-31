"""Health check endpoint with deep service checks."""

from fastapi import APIRouter

from app.ai.embeddings import embedding_service
from app.core.logging import get_logger
from app.rag.retriever import retriever

logger = get_logger(__name__)

router = APIRouter(tags=["health"])


@router.get("/api/health")
async def health_check():
    """Deep health check for all services."""
    checks = {
        "embedding_model": embedding_service.is_ready,
        "qdrant": retriever.is_healthy(),
    }

    all_healthy = all(checks.values())
    status = "healthy" if all_healthy else "degraded"

    return {
        "status": status,
        "checks": checks,
    }
