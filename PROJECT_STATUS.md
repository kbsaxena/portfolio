# Portfolio AI Assistant — Project Status

## Author: Kulbhushan Saxena (KB) | 2026

## What This Is
A personal portfolio website with an embedded AI assistant that uses RAG (Retrieval-Augmented Generation) to answer questions about Kulbhushan's resume, projects, and DSA code.

## Tech Stack
- **Frontend:** Plain HTML/CSS/JS (no framework)
- **Backend:** Python 3.12, FastAPI
- **LLM:** Groq API (Llama 3.3 70B, free tier)
- **Embeddings:** BGE-M3 (local, 1024d dense + sparse)
- **Vector DB:** Qdrant (Docker, hybrid search + RRF fusion)
- **Web Search:** SearXNG (self-hosted Docker)
- **Email:** Brevo transactional API
- **Deployment:** Docker Compose + Cloudflare Tunnel + Oracle VM (18GB RAM, 2 OCPUs)
- **Domain:** GoDaddy (DNS only)

## Current File Structure
```
portfolio/
├── frontend/
│   ├── index.html          ← SPA with all sections
│   ├── css/style.css       ← Dark/light theme, animations
│   ├── js/chat.js          ← Chat widget, DSA viewer, theme, accordions
│   ├── js/contact.js       ← Contact form handler
│   ├── favicon.svg         ← KB gradient icon
│   └── robots.txt
├── backend/
│   ├── app/
│   │   ├── main.py         ← FastAPI entry, middleware, routers, static mount
│   │   ├── config.py       ← Pydantic Settings (env vars)
│   │   ├── ai/
│   │   │   ├── orchestrator.py  ← Pipeline: guardrails→classify→tools→synthesize
│   │   │   ├── classifier.py   ← Groq query classification (rag|web|general|rag+web)
│   │   │   ├── synthesizer.py  ← Streaming response with system prompt
│   │   │   ├── llm.py          ← Groq client + circuit breaker + retry
│   │   │   ├── embeddings.py   ← BGE-M3 service
│   │   │   ├── memory.py       ← Session memory with TTL
│   │   │   ├── guardrails.py   ← Input/output sanitization
│   │   │   └── tools/          ← RAG, web search, general tools + registry
│   │   ├── rag/
│   │   │   ├── chunker.py      ← Markdown + Java chunking (400 tokens, 80 overlap)
│   │   │   ├── ingest.py       ← Load data into Qdrant
│   │   │   ├── retriever.py    ← Dense search with NamedVector
│   │   │   └── evaluate.py     ← MAP@K evaluation (99.6% MAP@5)
│   │   ├── api/routes/          ← chat, contact, dsa, execute, health, ingest, metrics
│   │   ├── middleware/          ← security headers, rate limiter, request ID/size/logging
│   │   ├── core/               ← circuit breaker, exceptions, logging
│   │   └── services/email.py   ← Brevo integration
│   ├── data/
│   │   ├── resume.md           ← Full resume (from PDF)
│   │   ├── projects.md         ← All project descriptions
│   │   └── dsa/src/            ← 319 Java DSA files (cloned from GitHub)
│   ├── Dockerfile
│   ├── requirements.txt
│   └── pyproject.toml
├── nginx/                       ← nginx.conf + security-headers.conf
├── searxng/settings.yml
├── cloudflare/config.yml
├── scripts/                     ← backup + health check
├── docker-compose.yml           ← 5 services (nginx, backend, qdrant, searxng, cloudflared)
├── docker-compose.dev.yml
├── Makefile
├── README.md
├── ARCHITECTURE.md
├── .env.example
├── .env                         ← (gitignored, has real keys)
├── .gitignore
├── .gitattributes
└── .pre-commit-config.yaml
```

## Key URLs
- LinkedIn: https://www.linkedin.com/in/kulbhushansaxena/
- GitHub: https://github.com/kbsaxena
- DSA Repo: https://github.com/kbsaxena/DSAJava
- Email: kulbhushan.saxena09@gmail.com

## RAG Evaluation
- MAP@5 = 99.6% (15 test queries)
- 905 chunks indexed (2 markdown + 319 Java files)
- Chunker: 400 tokens, 80 overlap, section prefixes

## What Works
- ✅ Chat with SSE streaming (word-by-word with cursor)
- ✅ RAG retrieval from resume/projects/DSA code
- ✅ Query classification (rag, web_search, general, rag+web)
- ✅ Session memory (multi-turn conversations)
- ✅ DSA code browser (click category → see problems → view code)
- ✅ Contact form (Brevo email)
- ✅ Dark/light theme
- ✅ Syntax highlighting for Java code
- ✅ Under the Hood section (collapsible accordions)
- ✅ Health check endpoint
- ✅ Rate limiting + security headers

## Known Issues / TODO
- [ ] DSA category cards need better hover animation (currently pale)
- [ ] "Under the Hood" accordion needs better visual styling
- [ ] Code execution (Judge0) not set up locally — needs full Docker Compose
- [ ] SearXNG not running locally — web search gracefully degrades
- [ ] Mobile hamburger menu not implemented (nav hidden on small screens)
- [ ] No "scroll to top" button
- [ ] OG preview image not created yet

## How to Run Locally
```bash
# Start Qdrant
docker start qdrant  # or: docker run -d -p 6333:6333 --name qdrant qdrant/qdrant:v1.12.1

# Start backend
cd backend
.venv\Scripts\activate
python -m uvicorn app.main:app --reload --host 0.0.0.0 --port 8000

# Ingest data (first time or after data changes)
python -m app.rag.ingest

# Open: http://localhost:8000
```

## Deployment (Oracle VM)
```bash
git clone https://github.com/kbsaxena/portfolio.git
cd portfolio
cp .env.example .env  # fill in keys
docker compose up -d --build
docker compose exec backend python -m app.rag.ingest
# Set up Cloudflare Tunnel token in .env
```
