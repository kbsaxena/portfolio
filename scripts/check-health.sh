#!/bin/bash
# Health check script for all portfolio services
set -euo pipefail

echo "🏥 Checking service health..."
echo "================================"

# Nginx health
echo -n "Nginx:    "
if curl -sf http://localhost/health > /dev/null 2>&1; then
    echo "✅ Healthy"
else
    echo "❌ Unhealthy"
fi

# Backend health
echo -n "Backend:  "
if curl -sf http://localhost/api/health > /dev/null 2>&1; then
    echo "✅ Healthy"
else
    echo "❌ Unhealthy"
fi

# Qdrant health
echo -n "Qdrant:   "
if curl -sf http://localhost:6333/healthz > /dev/null 2>&1; then
    echo "✅ Healthy"
else
    echo "❌ Unhealthy (or not exposed)"
fi

# SearXNG health
echo -n "SearXNG:  "
if curl -sf http://localhost:8081/healthz > /dev/null 2>&1; then
    echo "✅ Healthy"
else
    echo "❌ Unhealthy (or not exposed)"
fi

echo "================================"
echo "🐳 Docker containers:"
docker compose ps --format "table {{.Name}}\t{{.Status}}\t{{.Ports}}"
