"""Base tool interface and result dataclass."""

from abc import ABC, abstractmethod
from dataclasses import dataclass, field


@dataclass
class ToolResult:
    """Result from a tool execution."""

    content: str = ""
    confidence: float = 1.0
    source: str = ""
    citations: list[dict] = field(default_factory=list)

    def to_dict(self) -> dict:
        return {
            "content": self.content,
            "confidence": self.confidence,
            "source": self.source,
            "citations": self.citations,
        }


class BaseTool(ABC):
    """Abstract base class for all tools."""

    name: str = ""
    description: str = ""

    @abstractmethod
    async def execute(self, **kwargs) -> ToolResult:
        """Execute the tool and return a result."""
        ...
