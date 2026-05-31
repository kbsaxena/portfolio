"""Admin ingestion trigger endpoint."""

from fastapi import APIRouter, Header

from app.config import settings
from app.core.exceptions import AuthenticationError
from app.core.logging import get_logger
from app.rag.ingest import ingest_all

logger = get_logger(__name__)

router = APIRouter(prefix="/api/ingest", tags=["ingest"])


@router.post("")
async def trigger_ingestion(x_api_key: str = Header(...)):
    """Trigger document ingestion (admin only)."""
    if x_api_key != settings.admin_api_key:
        raise AuthenticationError("Invalid API key")

    logger.info("Ingestion triggered via API")
    result = await ingest_all()
    return {"status": "success", "result": result}
