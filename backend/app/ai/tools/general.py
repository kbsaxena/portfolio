"""General tool for queries that don't need external context."""

from app.ai.tools.base import BaseTool, ToolResult


class GeneralTool(BaseTool):
    """Handle general queries with no external context needed."""

    name = "general"
    description = "Handle general conversation without external context"

    async def execute(self, **kwargs) -> ToolResult:
        """Return empty context for general queries."""
        return ToolResult(content="", confidence=1.0, source="general")
