"""Application configuration using Pydantic Settings."""

from functools import cached_property

from pydantic import field_validator
from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    """Application settings loaded from environment variables."""

    model_config = SettingsConfigDict(
        env_file=("../.env", ".env"),
        env_file_encoding="utf-8",
        extra="ignore",
    )

    # Application
    environment: str = "development"
    debug: bool = False
    allowed_origin: str = "http://localhost:8000"

    # Groq LLM
    groq_api_key: str = ""
    groq_model: str = "llama-3.3-70b-versatile"
    groq_max_tokens: int = 2048
    groq_timeout: int = 30

    # Qdrant Vector Database
    qdrant_host: str = "localhost"
    qdrant_port: int = 6333
    qdrant_collection: str = "portfolio"

    # SearXNG Web Search
    searxng_url: str = "http://localhost:8080"
    searxng_timeout: int = 10

    # Brevo Email
    brevo_api_key: str = ""
    brevo_sender_email: str = ""
    brevo_recipient_email: str = ""

    # Embedding Model
    embedding_model: str = "BAAI/bge-m3"
    embedding_timeout: int = 5

    # Security
    admin_api_key: str = ""
    max_request_size: int = 10240
    rate_limit_chat: int = 30
    rate_limit_contact: int = 5

    # Conversation Memory
    max_conversation_turns: int = 10
    session_ttl_minutes: int = 30
    max_sessions: int = 1000

    # Circuit Breaker
    circuit_breaker_threshold: int = 5
    circuit_breaker_timeout: int = 60

    # RAG
    retrieval_top_k: int = 5
    confidence_threshold: float = 0.35

    # Performance
    max_concurrent_streams: int = 50
    request_timeout: int = 60

    @field_validator("groq_api_key")
    @classmethod
    def validate_groq_api_key(cls, v: str) -> str:
        if not v:
            raise ValueError("GROQ_API_KEY is required")
        return v

    @field_validator("admin_api_key")
    @classmethod
    def validate_admin_api_key(cls, v: str) -> str:
        if not v:
            raise ValueError("ADMIN_API_KEY is required")
        return v

    @cached_property
    def is_production(self) -> bool:
        return self.environment == "production"


settings = Settings()
