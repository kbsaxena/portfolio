# AI-Powered Portfolio

**Author:** Kulbhushan Saxena (KB)  
**Year:** 2026  
**License:** MIT

An intelligent portfolio website with a RAG-powered conversational AI assistant that answers questions about my professional experience using vector search, LLM generation, and web search fallback.

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Cloudflare Tunnel                      │
└─────────────────────┬───────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────┐
│              Nginx (Reverse Proxy + Static)               │
│         Rate Limiting · Gzip · Security Headers          │
└────────┬────────────────────────────────┬───────────────┘
         │                                │
┌────────▼────────┐            ┌─────────▼──────────────┐
│  Static Files   │            │   FastAPI Backend       │
│  (HTML/CSS/JS)  │            │   /api/* endpoints      │
└─────────────────┘            └────┬──────────┬────────┘
                                    │          │
                          ┌─────────▼──┐  ┌───▼────────┐
                          │   Qdrant   │  │  SearXNG   │
                          │ Vector DB  │  │ Web Search │
                          └────────────┘  └────────────┘
```

## Tech Stack

| Component | Technology | Purpose |
|-----------|-----------|---------|
| Frontend | Vanilla JS, CSS | SPA with chat widget |
| Backend | FastAPI (Python 3.12) | API, RAG pipeline, SSE streaming |
| Vector DB | Qdrant v1.12.1 | Semantic search over resume data |
| Web Search | SearXNG | Fallback for unknown queries |
| Reverse Proxy | Nginx 1.27 | Static serving, rate limiting, security |
| Tunnel | Cloudflare | Secure public access without open ports |
| Container | Docker Compose | Orchestration of all services |

## Quick Start

### Prerequisites
- Docker & Docker Compose
- Cloudflare Tunnel token (for production)

### Development

```bash
# Clone and configure
cp .env.example .env
# Edit .env with your API keys

# Start development stack (with hot reload)
make dev

# Or manually:
docker compose -f docker-compose.yml -f docker-compose.dev.yml up --build
```

Access at: http://localhost:8080

### Production

```bash
# Build and deploy
make deploy

# Check health
make health

# View logs
make logs
```

## Makefile Commands

| Command | Description |
|---------|-------------|
| `make dev` | Start development stack with hot reload |
| `make build` | Build production Docker images |
| `make deploy` | Deploy production stack (detached) |
| `make stop` | Stop all services |
| `make ingest` | Ingest resume data into Qdrant |
| `make logs` | Tail logs from all services |
| `make lint` | Run ruff linter checks |
| `make format` | Auto-format Python code |
| `make health` | Check health of all services |
| `make clean` | Remove containers, volumes, prune |

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/health` | GET | Health check |
| `/api/chat/stream` | GET | SSE chat streaming (query param: `q`) |
| `/api/chat` | POST | Non-streaming chat |
| `/api/contact` | POST | Contact form submission |
| `/api/dsa/categories/{category}` | GET | List DSA problems by category |
| `/api/dsa/code/{problem_id}` | GET | Get code for a DSA problem |
| `/metrics` | GET | Prometheus metrics |

## Security Features

- **Rate Limiting**: Nginx connection and request rate limits
- **Security Headers**: HSTS, CSP, X-Frame-Options, X-Content-Type-Options
- **Network Isolation**: Internal Docker network for backend services
- **No Open Ports**: Cloudflare Tunnel eliminates exposed ports in production
- **Input Validation**: Server-side validation on all endpoints
- **Honeypot**: Contact form bot detection
- **Non-root Containers**: Backend runs as non-root user

## Adding New Tools

To add a new tool/capability to the AI assistant:

1. **Create the tool function** in `backend/app/tools/`:
   ```python
   # backend/app/tools/my_tool.py
   async def my_tool(query: str) -> str:
       """Tool description for the LLM."""
       # Implementation
       return result
   ```

2. **Register in the agent** in `backend/app/agent/`:
   ```python
   from app.tools.my_tool import my_tool
   tools = [...existing_tools, my_tool]
   ```

3. **Rebuild and deploy**:
   ```bash
   make deploy
   ```

## Resource Usage

| Service | Memory | CPU | Storage |
|---------|--------|-----|---------|
| Nginx | ~10MB | Minimal | - |
| Backend | ~200MB | 0.5 core | - |
| Qdrant | ~100MB | 0.25 core | ~50MB |
| SearXNG | ~150MB | 0.25 core | - |
| Cloudflared | ~30MB | Minimal | - |
| **Total** | **~490MB** | **~1 core** | **~50MB** |

## License

Private project. All rights reserved.
