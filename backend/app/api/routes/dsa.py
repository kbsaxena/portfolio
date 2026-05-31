"""DSA file browser API — serves file lists and code content."""

import re
from pathlib import Path

from fastapi import APIRouter

from app.core.logging import get_logger

logger = get_logger(__name__)
router = APIRouter(prefix="/api/dsa", tags=["dsa"])

DSA_DIR = Path(__file__).parent.parent.parent.parent / "data" / "dsa" / "src"


@router.get("/categories/{category}")
async def list_problems(category: str):
    """List all Java files in a DSA category."""
    category_dir = DSA_DIR / category
    if not category_dir.exists() or not category_dir.is_dir():
        return {"problems": [], "count": 0}

    problems = []
    for f in sorted(category_dir.rglob("*.java")):
        name = f.stem
        # Convert CamelCase to readable: "SumOfTwo" -> "Sum Of Two"
        readable = re.sub(r"([A-Z])", r" \1", name).strip()
        problems.append({
            "file": f.name,
            "name": readable,
            "path": str(f.relative_to(DSA_DIR)).replace("\\", "/"),
        })

    return {"problems": problems, "count": len(problems)}


@router.get("/code/{category}/{filename}")
async def get_code(category: str, filename: str):
    """Get the source code of a specific DSA file."""
    category_dir = DSA_DIR / category
    if not category_dir.exists():
        return {"error": "Category not found", "code": ""}

    # Find the file (could be in subdirectory)
    matches = list(category_dir.rglob(filename))
    if not matches:
        return {"error": "File not found", "code": ""}

    file_path = matches[0]
    try:
        code = file_path.read_text(encoding="utf-8")
        return {"code": code, "file": filename, "category": category}
    except Exception as e:
        return {"error": str(e), "code": ""}
