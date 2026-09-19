# Environment Variables Contract

All configuration that changes between environments (Dev, Test, Prod) or contains secrets must be externalized.

## Core Application
* `SPRING_PROFILES_ACTIVE`: (e.g., `dev`, `prod`)
* `FRONTEND_BASE_URL`: For CORS configuration (e.g., `http://localhost:5173`)
* `SESSION_TIMEOUT_SECONDS`: (e.g., `86400`)

## Database (Supabase PostgreSQL)
* `DATABASE_URL`: JDBC connection string.
* `DATABASE_USERNAME`
* `DATABASE_PASSWORD`

## Redis
* `REDIS_HOST`
* `REDIS_PORT`
* `REDIS_PASSWORD`

## RabbitMQ
* `RABBITMQ_HOST`
* `RABBITMQ_PORT`
* `RABBITMQ_USERNAME`
* `RABBITMQ_PASSWORD`

## AI / RAG
* `LLM_PROVIDER`: (e.g., `openai`, `anthropic`, `ollama`)
* `LLM_API_KEY`
* `LLM_MODEL_NAME`
* `EMBEDDING_PROVIDER`
* `EMBEDDING_API_KEY`

## External APIs
* `CVE_API_BASE_URL`: Base URL for CVE lookups.
* `CVE_API_KEY`: (If required by the chosen provider).

**CRITICAL RULE**: Never commit actual credentials to Git. Provide `.env.example` templates in the repository.
