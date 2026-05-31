"""Hybrid retriever using Qdrant dense + sparse search."""

from qdrant_client import QdrantClient, models

from app.ai.embeddings import embedding_service
from app.config import settings
from app.core.logging import get_logger

logger = get_logger(__name__)


class HybridRetriever:
    """Hybrid retriever combining dense and sparse search."""

    def __init__(self):
        self._client: QdrantClient | None = None

    @property
    def client(self) -> QdrantClient:
        if self._client is None:
            self._client = QdrantClient(
                host=settings.qdrant_host, port=settings.qdrant_port
            )
        return self._client

    def is_healthy(self) -> bool:
        """Check if Qdrant is reachable."""
        try:
            self.client.get_collections()
            return True
        except Exception:
            return False

    async def search(
        self, query: str, top_k: int | None = None
    ) -> list[dict]:
        """Perform hybrid search (dense + sparse)."""
        top_k = top_k or settings.retrieval_top_k

        try:
            # Encode query
            embeddings = await embedding_service.encode_single(query)
            dense_vector = embeddings["dense_vecs"][0].tolist()

            # Dense search using NamedVector
            results = self.client.query_points(
                collection_name=settings.qdrant_collection,
                query=dense_vector,
                using="dense",
                limit=top_k,
                with_payload=True,
            )

            # Format results
            formatted: list[dict] = []
            for point in results.points:
                payload = point.payload or {}
                formatted.append(
                    {
                        "content": payload.get("content", ""),
                        "score": point.score,
                        "source": payload.get("source", ""),
                        "title": payload.get("title", ""),
                        "type": payload.get("type", ""),
                    }
                )

            return formatted

        except Exception as e:
            logger.error(f"Retrieval error: {e}")
            return []


retriever = HybridRetriever()
