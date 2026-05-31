"""Document chunking for markdown and Java files."""

import re

from app.core.logging import get_logger

logger = get_logger(__name__)

CHUNK_SIZE = 400
CHUNK_OVERLAP = 80
MIN_CHUNK_SIZE = 50


def chunk_markdown(text: str, source: str = "") -> list[dict]:
    """Chunk a markdown document by headers with hierarchy tracking."""
    sections = _split_by_headers(text)
    chunks: list[dict] = []

    for title, content, header_level in sections:
        content = content.strip()
        if not content:
            continue

        # Build section prefix from hierarchy
        section_prefix = f"{title}: " if title else ""

        # Small sections go as standalone chunks
        if len(content) <= CHUNK_SIZE:
            if len(content) >= MIN_CHUNK_SIZE:
                chunks.append(
                    {
                        "content": f"{section_prefix}{content}",
                        "metadata": {
                            "source": source,
                            "title": title,
                            "header_level": header_level,
                            "type": "markdown",
                        },
                    }
                )
            continue

        # Split larger sections into overlapping chunks
        words = content.split()
        start = 0
        while start < len(words):
            end = start + CHUNK_SIZE
            chunk_words = words[start:end]
            chunk_text = " ".join(chunk_words)

            if len(chunk_text) >= MIN_CHUNK_SIZE:
                chunks.append(
                    {
                        "content": f"{section_prefix}{chunk_text}",
                        "metadata": {
                            "source": source,
                            "title": title,
                            "header_level": header_level,
                            "type": "markdown",
                        },
                    }
                )

            start = end - CHUNK_OVERLAP

    logger.info(f"Chunked {source}: {len(chunks)} chunks")
    return chunks


def chunk_java_file(text: str, source: str = "") -> list[dict]:
    """Chunk a Java file with enriched metadata."""
    chunks: list[dict] = []

    # Extract class name
    class_match = re.search(
        r"(?:public\s+)?class\s+(\w+)", text
    )
    class_name = class_match.group(1) if class_match else "Unknown"

    # Extract method names
    method_names = re.findall(
        r"(?:public|private|protected)?\s*(?:static\s+)?[\w<>\[\]]+\s+(\w+)\s*\(",
        text,
    )

    # Split by methods
    method_pattern = re.compile(
        r"((?:\/\*\*[\s\S]*?\*\/\s*)?(?:@\w+.*\n)*\s*"
        r"(?:public|private|protected)\s+(?:static\s+)?[\w<>\[\]]+\s+\w+\s*\([^)]*\)\s*"
        r"(?:throws\s+[\w,\s]+)?\s*\{)",
        re.MULTILINE,
    )

    parts = method_pattern.split(text)

    # First part is class-level (imports, fields)
    if parts and parts[0].strip():
        class_header = parts[0].strip()
        if len(class_header) >= MIN_CHUNK_SIZE:
            chunks.append(
                {
                    "content": class_header,
                    "metadata": {
                        "source": source,
                        "class_name": class_name,
                        "method_names": method_names,
                        "type": "java",
                        "section": "class_header",
                    },
                }
            )

    # Process method chunks
    i = 1
    while i < len(parts):
        method_sig = parts[i] if i < len(parts) else ""
        method_body = parts[i + 1] if i + 1 < len(parts) else ""
        method_content = f"{method_sig}{method_body}".strip()

        if len(method_content) >= MIN_CHUNK_SIZE:
            # Extract this method's name
            name_match = re.search(r"(\w+)\s*\(", method_sig)
            method_name = name_match.group(1) if name_match else "unknown"

            chunks.append(
                {
                    "content": method_content,
                    "metadata": {
                        "source": source,
                        "class_name": class_name,
                        "method_name": method_name,
                        "method_names": method_names,
                        "type": "java",
                        "section": "method",
                    },
                }
            )
        i += 2

    # Fallback: if no methods found, chunk the whole file
    if not chunks:
        words = text.split()
        start = 0
        while start < len(words):
            end = start + CHUNK_SIZE
            chunk_text = " ".join(words[start:end])
            if len(chunk_text) >= MIN_CHUNK_SIZE:
                chunks.append(
                    {
                        "content": chunk_text,
                        "metadata": {
                            "source": source,
                            "class_name": class_name,
                            "method_names": method_names,
                            "type": "java",
                        },
                    }
                )
            start = end - CHUNK_OVERLAP

    logger.info(f"Chunked Java {source}: {len(chunks)} chunks")
    return chunks


def _split_by_headers(text: str) -> list[tuple[str, str, int]]:
    """Split markdown text by headers.

    Returns list of (title, content, header_level) tuples.
    """
    header_pattern = re.compile(r"^(#{1,6})\s+(.+)$", re.MULTILINE)
    sections: list[tuple[str, str, int]] = []

    matches = list(header_pattern.finditer(text))

    if not matches:
        return [("", text, 0)]

    # Content before first header
    if matches[0].start() > 0:
        pre_content = text[: matches[0].start()]
        if pre_content.strip():
            sections.append(("", pre_content.strip(), 0))

    for i, match in enumerate(matches):
        header_level = len(match.group(1))
        title = match.group(2).strip()

        # Content is between this header and the next
        start = match.end()
        end = matches[i + 1].start() if i + 1 < len(matches) else len(text)
        content = text[start:end].strip()

        sections.append((title, content, header_level))

    return sections
