"""Query classification for routing to appropriate tools."""

import json

from app.ai.llm import groq_client
from app.core.logging import get_logger

logger = get_logger(__name__)

_CLASSIFICATION_PROMPT = """You are a query classifier for Kulbhushan Saxena's portfolio assistant.
Classify the user's query into one of these categories:

- "rag": Questions about Kulbhushan's experience, skills, projects, education, or background
- "web_search": Questions requiring current/real-time information from the web
- "general": General conversation, greetings, or questions not about Kulbhushan
- "rag+web": Questions about Kulbhushan that may benefit from additional web context

Examples:
- "What projects has Kulbhushan worked on?" → "rag"
- "What is the latest version of Python?" → "web_search"
- "Hello, how are you?" → "general"
- "How does Kulbhushan's experience compare to industry trends?" → "rag+web"

Respond with ONLY a JSON object: {"category": "<category>", "confidence": <0.0-1.0>}
"""


async def classify_query(query: str) -> dict[str, str | float]:
    """Classify a user query into a routing category."""
    messages = [
        {"role": "system", "content": _CLASSIFICATION_PROMPT},
        {"role": "user", "content": query},
    ]

    try:
        response = await groq_client.generate(
            messages=messages,
            max_tokens=50,
            temperature=0.1,
        )

        # Parse JSON response
        result = json.loads(response.strip())
        category = result.get("category", "general")
        confidence = result.get("confidence", 0.5)

        # Validate category
        valid_categories = {"rag", "web_search", "general", "rag+web"}
        if category not in valid_categories:
            category = "general"

        logger.debug(
            f"Query classified as '{category}' with confidence {confidence}"
        )
        return {"category": category, "confidence": confidence}

    except (json.JSONDecodeError, KeyError) as e:
        logger.warning(f"Classification parse error: {e}, defaulting to general")
        return {"category": "general", "confidence": 0.5}
    except Exception as e:
        logger.error(f"Classification failed: {e}, defaulting to general")
        return {"category": "general", "confidence": 0.5}
