"""
Portfolio AI Assistant — FastAPI Backend
Author: Kulbhushan Saxena (KB)
Year: 2026
"""

from contextlib import asynccontextmanager
from pathlib import Path

from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
from fastapi.staticfiles import StaticFiles

from app.ai.embeddings import embedding_service
from app.ai.tools.registry import register_default_tools
from app.api.routes import chat, contact, dsa, execute, health, ingest, metrics, stats
from app.config import settings
from app.core.exceptions import AppError
from app.core.logging import get_logger, setup_logging
from app.middleware.rate_limiter import RateLimitMiddleware
from app.middleware.request_id import RequestIDMiddleware
from app.middleware.request_logging import RequestLoggingMiddleware
from app.middleware.request_size import RequestSizeMiddleware
from app.middleware.security_headers import SecurityHeadersMiddleware

logger = get_logger(__name__)


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan: startup and shutdown."""
    setup_logging(
        level="DEBUG" if settings.debug else "INFO",
        use_json=settings.is_production,
    )
    logger.info(f"Starting application in {settings.environment} mode")

    # Load embedding model
    await embedding_service.warmup()

    # Register tools
    register_default_tools()

    logger.info("Application startup complete")
    yield
    logger.info("Application shutting down")


app = FastAPI(
    title="Portfolio AI Assistant",
    version="1.0.0",
    lifespan=lifespan,
    docs_url=None if settings.is_production else "/docs",
    redoc_url=None if settings.is_production else "/redoc",
)

# --- Middleware (order matters: outermost first) ---
app.add_middleware(SecurityHeadersMiddleware)
app.add_middleware(
    CORSMiddleware,
    allow_origins=[settings.allowed_origin, "https://kbsaxena.in"],
    allow_credentials=True,
    allow_methods=["GET", "POST"],
    allow_headers=["Content-Type", "X-API-Key", "X-Request-ID"],
)
app.add_middleware(RateLimitMiddleware)
app.add_middleware(RequestSizeMiddleware)
app.add_middleware(RequestIDMiddleware)
app.add_middleware(RequestLoggingMiddleware)


# --- Exception Handlers ---
@app.exception_handler(AppError)
async def app_error_handler(request: Request, exc: AppError):
    return JSONResponse(
        status_code=exc.status_code,
        content=exc.to_dict(),
    )


@app.exception_handler(Exception)
async def general_exception_handler(request: Request, exc: Exception):
    logger.error(f"Unhandled exception: {exc}", exc_info=True)
    return JSONResponse(
        status_code=500,
        content={
            "error": "INTERNAL_ERROR",
            "message": "An unexpected error occurred",
        },
    )


# --- Routers ---
app.include_router(chat.router)
app.include_router(contact.router)
app.include_router(dsa.router)
app.include_router(execute.router)
app.include_router(health.router)
app.include_router(ingest.router)
app.include_router(metrics.router)
app.include_router(stats.router)

# --- Static files (dev mode) ---
if not settings.is_production:
    frontend_dir = Path(__file__).parent.parent.parent / "frontend"
    if frontend_dir.exists():
        app.mount("/", StaticFiles(directory=str(frontend_dir), html=True))
