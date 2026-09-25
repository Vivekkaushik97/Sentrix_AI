# Phase 0: Environment Variables Contract

This document defines all externalized configuration required by Sentrix AI. Actual credentials, secrets, or specific endpoint URLs must never be hardcoded into the source code or committed to version control.

## 1. Core Spring Boot Configuration
*   `SPRING_PROFILES_ACTIVE`: Defines the active environment profile (e.g., `dev`, `test`, `prod`).

## 2. Database (PostgreSQL / Supabase)
*   `DATABASE_URL`: JDBC URL connection string (e.g., `jdbc:postgresql://host:5432/dbname`).
*   `DATABASE_USERNAME`: PostgreSQL user.
*   `DATABASE_PASSWORD`: PostgreSQL password.
*   *Note: HikariCP connection pool settings will be configured in `application.yml` but rely on these credentials.*

## 3. Redis Cache & Session
*   `REDIS_HOST`: Hostname or IP of the Redis server.
*   `REDIS_PORT`: Redis port (default 6379).
*   `REDIS_PASSWORD`: Redis authentication password, if applicable.
*   `SESSION_TIMEOUT`: Duration in seconds for session validity (e.g., 604800 for 7 days).

## 4. RabbitMQ (Message Broker)
*   `RABBITMQ_HOST`: Hostname or IP of the RabbitMQ server.
*   `RABBITMQ_PORT`: RabbitMQ port (default 5672).
*   `RABBITMQ_USERNAME`: RabbitMQ user.
*   `RABBITMQ_PASSWORD`: RabbitMQ password.

## 5. Spring AI & LLM Integration
*   `LLM_PROVIDER`: Identifier for the chosen LLM provider (e.g., `openai`, `anthropic`, `ollama`).
*   `LLM_API_KEY`: Authentication key for the LLM provider.
*   `LLM_MODEL_NAME`: Specific model to use (e.g., `gpt-4-turbo`, `claude-3-opus`).
*   `EMBEDDING_PROVIDER`: Identifier for the chosen embedding model provider.
*   `EMBEDDING_API_KEY`: Authentication key for the embedding provider.
*   `EMBEDDING_MODEL_NAME`: Specific embedding model to use.

## 6. External APIs (CVE)
*   `CVE_API_BASE_URL`: Base URL for the chosen CVE data provider.
*   `CVE_API_KEY`: Authentication key, if the provider requires one.

## 7. Storage (EVTX Uploads & PDF Reports)
*   `STORAGE_BASE_PATH`: Local directory path or S3 bucket name for storing files.
*   *If using S3/Cloud Storage (TO BE DECIDED):*
    *   `STORAGE_ACCESS_KEY`
    *   `STORAGE_SECRET_KEY`
    *   `STORAGE_REGION`

## 8. Web & Security
*   `FRONTEND_BASE_URL`: The exact URL of the deployed React frontend (e.g., `https://app.sentrix-ai.com`).
*   `CORS_ALLOWED_ORIGINS`: Comma-separated list of allowed origins (usually matches `FRONTEND_BASE_URL`).

## 9. Implementation Notes
*   A `.env.example` file must be provided in the root directory containing placeholder values for all the above variables.
*   Developers must create their own local `.env` file (which is git-ignored) to run the application locally.
*   In production (Docker/CI), these will be injected as true environment variables.
