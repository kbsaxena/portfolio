"""Document ingestion pipeline for Qdrant."""

import asyncio
import uuid
from pathlib import Path

from qdrant_client import QdrantClient, models

from app.ai.embeddings import embedding_service
from app.config import settings
from app.core.logging import get_logger
from app.rag.chunker import chunk_java_file, chunk_markdown

logger = get_logger(__name__)

DATA_DIR = Path(__file__).parent.parent.parent / "data"
DSA_DIR = DATA_DIR / "dsa" / "src"


def _get_client() -> QdrantClient:
    """Get a Qdrant client."""
    return QdrantClient(host=settings.qdrant_host, port=settings.qdrant_port)


def _ensure_collection(client: QdrantClient) -> None:
    """Ensure the Qdrant collection exists with proper config."""
    collections = [c.name for c in client.get_collections().collections]

    if settings.qdrant_collection not in collections:
        client.create_collection(
            collection_name=settings.qdrant_collection,
            vectors_config={
                "dense": models.VectorParams(
                    size=1024,
                    distance=models.Distance.COSINE,
                )
            },
            sparse_vectors_config={
                "sparse": models.SparseVectorParams(
                    modifier=models.Modifier.IDF,
                )
            },
        )
        logger.info(f"Created collection: {settings.qdrant_collection}")
    else:
        logger.info(f"Collection exists: {settings.qdrant_collection}")


async def ingest_all() -> dict[str, int]:
    """Ingest all documents from data directories."""
    client = _get_client()
    _ensure_collection(client)

    all_chunks: list[dict] = []

    # Process markdown files
    md_files = list(DATA_DIR.glob("*.md"))
    for md_file in md_files:
        text = md_file.read_text(encoding="utf-8")
        chunks = chunk_markdown(text, source=md_file.name)
        all_chunks.extend(chunks)

    # Process Java files
    if DSA_DIR.exists():
        java_files = list(DSA_DIR.rglob("*.java"))
        for java_file in java_files:
            text = java_file.read_text(encoding="utf-8")
            relative_path = str(java_file.relative_to(DSA_DIR))
            chunks = chunk_java_file(text, source=relative_path)
            all_chunks.extend(chunks)

    if not all_chunks:
        logger.warning("No chunks to ingest")
        return {"total_chunks": 0, "md_files": len(md_files), "java_files": 0}

    # Batch encode and upsert
    batch_size = 4
    total_upserted = 0

    for i in range(0, len(all_chunks), batch_size):
        batch = all_chunks[i : i + batch_size]
        texts = [chunk["content"] for chunk in batch]

        try:
            embeddings = await asyncio.wait_for(
                embedding_service.encode(texts),
                timeout=60.0,
            )

            points = []
            for j, chunk in enumerate(batch):
                dense_vector = embeddings["dense_vecs"][j].tolist()

                # Build sparse vector
                sparse_data = embeddings["lexical_weights"][j]
                sparse_indices = list(sparse_data.keys())
                sparse_values = list(sparse_data.values())

                point = models.PointStruct(
                    id=str(uuid.uuid4()),
                    vector={
                        "dense": dense_vector,
                        "sparse": models.SparseVector(
                            indices=sparse_indices,
                            values=sparse_values,
                        ),
                    },
                    payload={
                        "content": chunk["content"],
                        **chunk["metadata"],
                    },
                )
                points.append(point)

            client.upsert(
                collection_name=settings.qdrant_collection,
                points=points,
            )
            total_upserted += len(points)
            logger.info(
                f"Upserted batch {i // batch_size + 1}: "
                f"{len(points)} points"
            )

        except asyncio.TimeoutError:
            logger.error(f"Timeout encoding batch at index {i}")
        except Exception as e:
            logger.error(f"Error ingesting batch at index {i}: {e}")

    logger.info(f"Ingestion complete: {total_upserted} total points")
    return {
        "total_chunks": total_upserted,
        "md_files": len(md_files),
        "java_files": len(list(DSA_DIR.rglob("*.java"))) if DSA_DIR.exists() else 0,
    }


if __name__ == "__main__":
    import sys

    from app.core.logging import setup_logging

    setup_logging(level="INFO", use_json=False)

    async def main():
        await embedding_service.warmup()
        result = await ingest_all()
        print(f"Ingestion result: {result}")

    asyncio.run(main())
    sys.exit(0)
