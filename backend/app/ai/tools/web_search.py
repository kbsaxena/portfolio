"""Web search tool using SearXNG."""

import httpx

from app.ai.tools.base import BaseTool, ToolResult
from app.config import settings
from app.core.logging import get_logger

logger = get_logger(__name__)


class WebSearchTool(BaseTool):
    """Search the web using SearXNG."""

    name = "web_search"
    description = "Search the web for current information"

    async def execute(self, **kwargs) -> ToolResult:
        """Execute a web search."""
        query = kwargs.get("query", "")
        if not query:
            return ToolResult(content="", confidence=0.0, source="web_search")

        try:
            async with httpx.AsyncClient(
                timeout=settings.searxng_timeout
            ) as client:
                response = await client.get(
                    f"{settings.searxng_url}/search",
                    params={
                        "q": query,
                        "format": "json",
                        "engines": "google,duckduckgo",
                        "language": "en",
                    },
                )
                response.raise_for_status()
                data = response.json()

            results = data.get("results", [])[:5]

            if not results:
                return ToolResult(
                    content="", confidence=0.0, source="web_search"
                )

            content_parts: list[str] = []
            citations: list[dict] = []

            for result in results:
                title = result.get("title", "")
                snippet = result.get("content", "")
                url = result.get("url", "")

                content_parts.append(f"**{title}**\n{snippet}")
                citations.append({"source": url, "title": title})

            return ToolResult(
                content="\n\n".join(content_parts),
                confidence=0.8,
                source="web_search",
                citations=citations,
            )

        except httpx.TimeoutException:
            logger.warning("Web search timed out")
            return ToolResult(content="", confidence=0.0, source="web_search")
        except Exception as e:
            logger.error(f"Web search error: {e}")
            return ToolResult(content="", confidence=0.0, source="web_search")
