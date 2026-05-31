"""RAG tool for retrieving context from the vector database."""

from app.ai.tools.base import BaseTool, ToolResult
from app.core.logging import get_logger
from app.rag.retriever import retriever

logger = get_logger(__name__)


class RAGTool(BaseTool):
    """Retrieve relevant context from the portfolio knowledge base."""

    name = "rag_search"
    description = "Search Kulbhushan's portfolio knowledge base"

    async def execute(self, **kwargs) -> ToolResult:
        """Execute RAG retrieval."""
        query = kwargs.get("query", "")
        if not query:
            return ToolResult(content="", confidence=0.0, source="rag")

        try:
            results = await retriever.search(query)

            if not results:
                return ToolResult(content="", confidence=0.0, source="rag")

            # Combine results
            content_parts: list[str] = []
            citations: list[dict] = []
            total_score = 0.0

            for result in results:
                content_parts.append(result["content"])
                total_score += result.get("score", 0.0)
                if result.get("source"):
                    citations.append(
                        {
                            "source": result["source"],
                            "title": result.get("title", ""),
                        }
                    )

            avg_confidence = total_score / len(results) if results else 0.0

            return ToolResult(
                content="\n\n".join(content_parts),
                confidence=avg_confidence,
                source="rag",
                citations=citations,
            )

        except Exception as e:
            logger.error(f"RAG tool error: {e}")
            return ToolResult(content="", confidence=0.0, source="rag")
