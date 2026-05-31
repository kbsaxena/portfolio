"""DSA file browser endpoint."""

from pathlib import Path

from fastapi import APIRouter

from app.core.exceptions import ValidationError
from app.core.logging import get_logger

logger = get_logger(__name__)

router = APIRouter(prefix="/api/dsa", tags=["dsa"])

DSA_DIR = Path(__file__).parent.parent.parent.parent / "data" / "dsa" / "src"


@router.get("/files")
async def list_dsa_files():
    """List all DSA Java files."""
    if not DSA_DIR.exists():
        return {"files": []}

    files: list[dict] = []
    for java_file in sorted(DSA_DIR.rglob("*.java")):
        relative = java_file.relative_to(DSA_DIR)
        files.append(
            {
                "path": str(relative).replace("\\", "/"),
                "name": java_file.stem,
                "size": java_file.stat().st_size,
            }
        )

    return {"files": files, "total": len(files)}


@router.get("/file/{file_path:path}")
async def get_dsa_file(file_path: str):
    """Get contents of a specific DSA file."""
    # Prevent path traversal
    if ".." in file_path:
        raise ValidationError("Invalid file path")

    full_path = DSA_DIR / file_path
    if not full_path.exists() or not full_path.is_file():
        raise ValidationError("File not found")

    if not str(full_path).startswith(str(DSA_DIR)):
        raise ValidationError("Access denied")

    content = full_path.read_text(encoding="utf-8")
    return {
        "path": file_path,
        "name": full_path.stem,
        "content": content,
        "language": "java",
    }
