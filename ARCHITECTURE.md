# Architecture Document

## System Overview

```
┌──────────────────────────────────────────────────────────────────┐
│                         INTERNET                                  │
└──────────────────────────────┬───────────────────────────────────┘
                               │
┌──────────────────────────────▼───────────────────────────────────┐
│                    Cloudflare Tunnel (cloudflared)                 │
│              Secure ingress without exposed ports                  │
└──────────────────────────────┬───────────────────────────────────┘
                               │
┌──────────────────────────────▼───────────────────────────────────┐
│                         PUBLIC NETWORK                             │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │              Nginx 1.27 (Reverse Proxy)                      │ │
│  │  • Static file serving (frontend SPA)                        │ │
│  │  • Rate limiting (10 req/s per IP)                           │ │
│  │  • Gzip compression                                          │ │
│  │  • Security headers (HSTS, CSP, etc.)                        │ │
│  │  • Connection limits (20 per IP)                             │ │
│  │  • JSON structured logging                                   │ │
│  └──────────────────────────┬──────────────────────────────────┘ │
└─────────────────────────────┼────────────────────────────────────┘
                              │
┌─────────────────────────────▼────────────────────────────────────┐
│                       INTERNAL NETWORK                             │
│                                                                    │
│  ┌────────────────────────────────────────────────────────────┐  │
│  │              FastAPI Backend (Python 3.12)                   │  │
│  │  • RAG pipeline (embed → search → generate)                 │  │
│  │  • SSE streaming responses                                  │  │
│  │  • Rate limiting middleware                                  │  │
│  │  • Prometheus metrics                                        │  │
│  │  • Contact form handling                                     │  │
│  │  • DSA code serving                                          │  │
│  └──────┬─────────────────────────────────┬───────────────────┘  │
│         │                                 │                        │
│  ┌──────▼──────────────┐    ┌────────────▼────────────────────┐  │
│  │   Qdrant v1.12.1    │    │       SearXNG 2024.12.23        │  │
│  │   Vector Database    │    │       Meta Search Engine         │  │
│  │  • Resume embeddings │    │  • Google, Bing, DuckDuckGo     │  │
│  │  • Semantic search   │    │  • Wikipedia, GitHub, SO         │  │
│  │  • Persistent volume │    │  • Fallback for unknown queries  │  │
│  └─────────────────────┘    └─────────────────────────────────┘  │
│                                                                    │
└────────────────────────────────────────────────────────────────────┘
```

## Component Details

### 1. Cloudflare Tunnel (cloudflared)
- **Purpose**: Secure public access without exposing ports
- **Image**: `cloudflare/cloudflared:latest`
- **Network**: Public only
- **Config**: Token-based authentication via environment variable

### 2. Nginx Reverse Proxy
- **Purpose**: Static serving, API proxying, security enforcement
- **Image**: `nginx:1.27-alpine`
- **Network**: Public + Internal (bridge between internet and backend)
- **Features**: Rate limiting, gzip, security headers, JSON logging
- **Static Root**: `/usr/share/nginx/html` (frontend SPA)

### 3. FastAPI Backend
- **Purpose**: Core application logic, RAG pipeline, API endpoints
- **Runtime**: Python 3.12-slim, non-root user
- **Network**: Internal only
- **Port**: 8000 (internal)
- **Key Modules**:
  - `app.main` - FastAPI application entry point
  - `app.api.chat` - Chat endpoints (streaming + non-streaming)
  - `app.api.contact` - Contact form handler
  - `app.api.dsa` - DSA problems and code serving
  - `app.rag.pipeline` - RAG orchestration
  - `app.rag.embeddings` - Text embedding generation
  - `app.rag.retriever` - Qdrant vector search
  - `app.rag.generator` - LLM response generation
  - `app.tools.web_search` - SearXNG integration
  - `app.middleware.rate_limiter` - Request rate limiting
  - `app.ingest` - Data ingestion into Qdrant

### 4. Qdrant Vector Database
- **Purpose**: Store and search resume embeddings
- **Image**: `qdrant/qdrant:v1.12.1`
- **Network**: Internal only
- **Port**: 6333 (gRPC: 6334)
- **Storage**: Persistent Docker volume (`qdrant_data`)
- **Collections**: Resume chunks with metadata

### 5. SearXNG Meta Search
- **Purpose**: Web search fallback for queries outside resume scope
- **Image**: `searxng/searxng:2024.12.23`
- **Network**: Internal only
- **Port**: 8080
- **Engines**: Google, Bing, DuckDuckGo, Wikipedia, GitHub, StackOverflow

### 6. Frontend SPA
- **Purpose**: User interface with chat widget
- **Technology**: Vanilla HTML/CSS/JavaScript (no framework)
- **Served by**: Nginx static file serving
- **Features**: Dark/light theme, responsive, SSE streaming, DSA viewer

### 7. Chat Widget
- **Purpose**: Conversational AI interface
- **Features**: SSE streaming, cached responses, follow-up suggestions, typing indicator
- **Connection**: EventSource to `/api/chat/stream`

### 8. RAG Pipeline
- **Purpose**: Retrieve relevant context and generate answers
- **Flow**: Embed query → Vector search → (Optional web search) → LLM generation
- **Embedding Model**: Configured via environment variable
- **LLM**: Configured via environment variable (supports multiple providers)

### 9. Rate Limiter Middleware
- **Purpose**: Protect backend from abuse
- **Implementation**: Token bucket algorithm per IP
- **Limits**: Configurable via environment variables

### 10. Prometheus Metrics
- **Purpose**: Observability and monitoring
- **Endpoint**: `/metrics`
- **Metrics**: Request count, latency, error rate, RAG pipeline timing

### 11. Contact Form Handler
- **Purpose**: Process contact form submissions
- **Validation**: Server-side input validation
- **Anti-spam**: Honeypot field detection
- **Storage**: Configurable (email, database, or file)

### 12. DSA Code Server
- **Purpose**: Serve algorithm implementations for the fundamentals section
- **Endpoints**: Category listing, problem listing, code retrieval
- **Storage**: File-based code repository

### 13. Data Ingestion
- **Purpose**: Process and embed resume data into Qdrant
- **Command**: `python -m app.ingest`
- **Process**: Chunk text → Generate embeddings → Upsert to Qdrant

### 14. Security Layer
- **Components**: Nginx headers, rate limiting, network isolation, non-root containers
- **Headers**: HSTS, CSP, X-Frame-Options, X-Content-Type-Options, Permissions-Policy

## Docker Network Topology

```
┌─────────────────────────────────────────┐
│            PUBLIC NETWORK                 │
│                                          │
│   ┌──────────┐      ┌──────────────┐   │
│   │cloudflared│      │    nginx     │   │
│   └──────────┘      └──────────────┘   │
│                                          │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│           INTERNAL NETWORK               │
│                                          │
│   ┌──────────┐  ┌────────┐  ┌───────┐  │
│   │  nginx   │  │backend │  │qdrant │  │
│   └──────────┘  └────────┘  └───────┘  │
│                  ┌────────┐             │
│                  │searxng │             │
│                  └────────┘             │
└─────────────────────────────────────────┘
```

- **Public network**: Cloudflared ↔ Nginx (ingress path)
- **Internal network**: Nginx ↔ Backend ↔ Qdrant/SearXNG (isolated)
- Nginx bridges both networks (only component with dual access)

## Request Lifecycle

### Chat Query (Complete Flow)

```
1. User types question in chat widget
2. Browser creates EventSource: GET /api/chat/stream?q=...
3. Nginx receives request
   → Rate limit check (10 req/s per IP)
   → Proxy to backend:8000
4. FastAPI receives request
   → Validate input
   → Start SSE response
5. RAG Pipeline executes:
   a. Embed query using embedding model
   b. Search Qdrant for top-k similar chunks
   c. Score relevance (confidence threshold)
   d. If low confidence → query SearXNG for web results
   e. Combine context (vector results + web results)
   f. Send prompt + context to LLM
   g. Stream tokens back via SSE
6. Browser receives SSE events
   → Parse JSON tokens
   → Append to message with cursor animation
   → Remove cursor on completion
7. Update questions counter (localStorage)
8. Show follow-up suggestions
```

### Static Page Load

```
1. Browser requests /
2. Cloudflare Tunnel → Nginx
3. Nginx serves index.html from /usr/share/nginx/html
4. Browser loads CSS, JS, favicon (cached 7 days)
5. JavaScript initializes:
   → Theme from localStorage
   → Questions count from localStorage
   → IntersectionObserver for timeline
   → Event listeners for chat, DSA, accordions
```

### Contact Form Submission

```
1. User fills form and submits
2. Client-side validation (email format, required fields)
3. Honeypot check (if filled → silent reject)
4. POST /api/contact with JSON body
5. Nginx proxies to backend
6. Backend validates input
7. Process submission (email/store)
8. Return success/error response
9. Display status message to user
```

## Security Summary

| Layer | Protection |
|-------|-----------|
| Network | Cloudflare DDoS, no exposed ports |
| Transport | HTTPS via Cloudflare, HSTS header |
| Application | Rate limiting, input validation, CSP |
| Container | Non-root user, read-only mounts, internal network |
| Data | No PII stored, honeypot anti-spam |

## Performance Characteristics

| Metric | Target | Achieved |
|--------|--------|----------|
| Static page load | < 1s | ~300ms (cached) |
| Chat first token | < 2s | ~1.5s |
| Chat full response | < 5s | ~3s average |
| Vector search | < 100ms | ~50ms |
| Memory footprint | < 1GB | ~490MB |
