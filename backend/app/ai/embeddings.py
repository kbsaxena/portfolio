"""BGE-M3 embedding service."""

import asyncio
from typing import Any

from app.config import settings
from app.core.logging import get_logger

logger = get_logger(__name__)


class EmbeddingService:
    """Manages the BGE-M3 embedding model."""

    def __init__(self):
        self._model: Any = None
        self._ready = False

    @property
    def is_ready(self) -> bool:
        return self._ready

    async def warmup(self) -> None:
        """Load the embedding model."""
        logger.info(f"Loading embedding model: {settings.embedding_model}")
        try:
            loop = asyncio.get_event_loop()
            self._model = await loop.run_in_executor(None, self._load_model)
            # Warmup encode
            await self.encode(["warmup"])
            self._ready = True
            logger.info("Embedding model loaded successfully")
        except Exception as e:
            logger.error(f"Failed to load embedding model: {e}")
            raise

    def _load_model(self):
        from FlagEmbedding import BGEM3FlagModel

        return BGEM3FlagModel(settings.embedding_model, use_fp16=True)

    async def encode(
        self, texts: list[str], batch_size: int = 12
    ) -> dict[str, Any]:
        """Encode texts to dense and sparse vectors."""
        loop = asyncio.get_event_loop()
        try:
            result = await asyncio.wait_for(
                loop.run_in_executor(
                    None,
                    lambda: self._model.encode(
                        texts,
                        batch_size=batch_size,
                        return_dense=True,
                        return_sparse=True,
                    ),
                ),
                timeout=60.0,
            )
            return result
        except asyncio.TimeoutError:
            logger.error("Embedding encode timed out after 60s")
            raise

    async def encode_single(self, text: str) -> dict[str, Any]:
        """Encode a single text."""
        return await self.encode([text])


embedding_service = EmbeddingService()
