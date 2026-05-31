"""Tool registry for managing available tools."""

from app.ai.tools.base import BaseTool
from app.core.logging import get_logger

logger = get_logger(__name__)


class ToolRegistry:
    """Registry for managing tool instances."""

    def __init__(self):
        self._tools: dict[str, BaseTool] = {}

    def register(self, tool: BaseTool) -> None:
        """Register a tool."""
        self._tools[tool.name] = tool
        logger.info(f"Registered tool: {tool.name}")

    def get(self, name: str) -> BaseTool | None:
        """Get a tool by name."""
        return self._tools.get(name)

    def list_tools(self) -> list[str]:
        """List all registered tool names."""
        return list(self._tools.keys())


tool_registry = ToolRegistry()


def register_default_tools() -> None:
    """Register all default tools."""
    from app.ai.tools.general import GeneralTool
    from app.ai.tools.rag_tool import RAGTool
    from app.ai.tools.web_search import WebSearchTool

    tool_registry.register(RAGTool())
    tool_registry.register(WebSearchTool())
    tool_registry.register(GeneralTool())
    logger.info(f"Registered {len(tool_registry.list_tools())} default tools")
