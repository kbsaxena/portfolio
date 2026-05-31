"""Code execution endpoint using Judge0."""

import httpx
from fastapi import APIRouter
from pydantic import BaseModel, Field

from app.core.exceptions import ServiceUnavailableError, ValidationError
from app.core.logging import get_logger

logger = get_logger(__name__)

router = APIRouter(prefix="/api/execute", tags=["execute"])

JUDGE0_URL = "http://localhost:2358"

# Language ID mapping for Judge0
LANGUAGE_IDS = {
    "java": 62,
    "python": 71,
    "javascript": 63,
    "c": 50,
    "cpp": 54,
}


class ExecuteRequest(BaseModel):
    source_code: str = Field(..., min_length=1, max_length=10000)
    language: str = Field(..., pattern=r"^(java|python|javascript|c|cpp)$")
    stdin: str = ""


@router.post("")
async def execute_code(body: ExecuteRequest):
    """Execute code using Judge0 sandbox."""
    language_id = LANGUAGE_IDS.get(body.language)
    if not language_id:
        raise ValidationError(f"Unsupported language: {body.language}")

    try:
        async with httpx.AsyncClient(timeout=30) as client:
            # Submit code
            response = await client.post(
                f"{JUDGE0_URL}/submissions",
                params={"base64_encoded": "false", "wait": "true"},
                json={
                    "source_code": body.source_code,
                    "language_id": language_id,
                    "stdin": body.stdin,
                    "cpu_time_limit": 5,
                    "memory_limit": 128000,
                },
            )
            response.raise_for_status()
            result = response.json()

        return {
            "stdout": result.get("stdout", ""),
            "stderr": result.get("stderr", ""),
            "compile_output": result.get("compile_output", ""),
            "status": result.get("status", {}).get("description", "Unknown"),
            "time": result.get("time"),
            "memory": result.get("memory"),
        }

    except httpx.TimeoutException:
        raise ServiceUnavailableError("Code execution timed out")
    except httpx.HTTPStatusError as e:
        logger.error(f"Judge0 error: {e}")
        raise ServiceUnavailableError("Code execution service unavailable")
    except Exception as e:
        logger.error(f"Execution error: {e}")
        raise ServiceUnavailableError("Code execution failed")
