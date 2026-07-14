.PHONY: dev build deploy stop ingest logs lint format health clean

# Development with hot reload
dev:
	docker compose -f docker-compose.yml -f docker-compose.dev.yml up --build

# Build production images
build:
	docker compose build

# Deploy production stack
deploy:
	docker compose up -d --build

# Stop all services
stop:
	docker compose down

# Ingest data into Qdrant
ingest:
	docker compose exec backend python -m app.rag.ingest

# View logs
logs:
	docker compose logs -f --tail=100

# Lint Python code
lint:
	cd backend && ruff check . && ruff format --check .

# Format Python code
format:
	cd backend && ruff check --fix . && ruff format .

# Health check
health:
	bash scripts/check-health.sh

# Clean up everything
clean:
	docker compose down -v --remove-orphans
	docker system prune -f
