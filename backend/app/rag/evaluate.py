"""RAG evaluation using MAP@K metric."""

import asyncio

from app.core.logging import get_logger
from app.rag.retriever import retriever

logger = get_logger(__name__)


def average_precision_at_k(
    relevant: list[str], retrieved: list[str], k: int
) -> float:
    """Calculate Average Precision at K."""
    if not relevant:
        return 0.0

    retrieved_k = retrieved[:k]
    hits = 0
    sum_precision = 0.0

    for i, doc in enumerate(retrieved_k):
        if doc in relevant:
            hits += 1
            precision_at_i = hits / (i + 1)
            sum_precision += precision_at_i

    return sum_precision / min(len(relevant), k)


def mean_average_precision_at_k(
    queries: list[dict[str, list[str]]], k: int = 5
) -> float:
    """Calculate Mean Average Precision at K across multiple queries.

    Each query dict should have:
        - "query": the search query string
        - "relevant": list of relevant document source identifiers
    """
    if not queries:
        return 0.0

    ap_scores: list[float] = []

    for q in queries:
        relevant = q.get("relevant", [])
        retrieved = q.get("retrieved", [])
        ap = average_precision_at_k(relevant, retrieved, k)
        ap_scores.append(ap)

    return sum(ap_scores) / len(ap_scores)


async def evaluate_retriever(
    test_queries: list[dict],
    k: int = 5,
) -> dict[str, float]:
    """Evaluate the retriever on test queries.

    Each test query should have:
        - "query": search query string
        - "relevant": list of expected source identifiers
    """
    results: list[dict[str, list[str]]] = []

    for test in test_queries:
        query = test["query"]
        relevant = test["relevant"]

        try:
            search_results = await retriever.search(query, top_k=k)
            retrieved = [r["source"] for r in search_results]
            print(f"  ✓ '{query[:40]}...' → {len(search_results)} results")
        except Exception as e:
            print(f"  ✗ '{query[:40]}...' → ERROR: {e}")
            retrieved = []

        results.append({"relevant": relevant, "retrieved": retrieved})

    map_k = mean_average_precision_at_k(results, k=k)
    logger.info(f"MAP@{k} = {map_k:.4f}")

    return {"map_at_k": map_k, "k": k, "num_queries": len(test_queries)}


if __name__ == "__main__":
    from app.ai.embeddings import embedding_service

    test_queries = [
        {
            "query": "What programming languages does Kulbhushan know?",
            "relevant": ["resume.md"],
        },
        {
            "query": "What projects has Kulbhushan worked on?",
            "relevant": ["projects.md"],
        },
        {
            "query": "Tell me about the enterprise AI chat platform",
            "relevant": ["resume.md", "projects.md"],
        },
        {
            "query": "What is Kulbhushan's experience with Kubernetes?",
            "relevant": ["resume.md"],
        },
        {
            "query": "Explain the two sum problem",
            "relevant": ["DSA"],
        },
        {
            "query": "What databases does Kulbhushan know?",
            "relevant": ["resume.md"],
        },
        {
            "query": "Tell me about RAG and vector databases experience",
            "relevant": ["resume.md", "projects.md"],
        },
        {
            "query": "Where did Kulbhushan work before Hexagon?",
            "relevant": ["resume.md"],
        },
        {
            "query": "How does the natural language to SQL engine work?",
            "relevant": ["resume.md", "projects.md"],
        },
        {
            "query": "What is Kulbhushan's education?",
            "relevant": ["resume.md"],
        },
    ]

    async def main():
        print("Loading embedding model...")
        await embedding_service.warmup()
        if not embedding_service.is_ready:
            print("ERROR: Embedding model failed to load!")
            return
        print(f"Model loaded: {embedding_service.is_ready}")
        print("Running evaluation (10 queries)...")
        result = await evaluate_retriever(test_queries)
        print(f"\n{'='*50}")
        print(f"  MAP@{result['k']} = {result['map_at_k']:.4f}")
        print(f"  Queries evaluated: {result['num_queries']}")
        print(f"{'='*50}")

    asyncio.run(main())
