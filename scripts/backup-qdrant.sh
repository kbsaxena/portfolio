#!/bin/bash
# Backup Qdrant vector database volume
set -euo pipefail

BACKUP_DIR="./backups"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE="${BACKUP_DIR}/qdrant_backup_${TIMESTAMP}.tar.gz"

mkdir -p "$BACKUP_DIR"

echo "🔄 Backing up Qdrant data..."
docker run --rm \
    -v portfolio_qdrant_data:/data:ro \
    -v "$(pwd)/${BACKUP_DIR}:/backup" \
    alpine tar czf "/backup/qdrant_backup_${TIMESTAMP}.tar.gz" -C /data .

echo "✅ Backup saved to: ${BACKUP_FILE}"
echo "📦 Size: $(du -h "$BACKUP_FILE" | cut -f1)"

# Keep only last 5 backups
ls -t "${BACKUP_DIR}"/qdrant_backup_*.tar.gz 2>/dev/null | tail -n +6 | xargs -r rm
echo "🧹 Old backups cleaned (keeping last 5)"
