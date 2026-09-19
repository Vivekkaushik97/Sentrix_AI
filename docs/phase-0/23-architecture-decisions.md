# Architectural Decision Records (ADRs)

## ADR-001 — Java-centric architecture
* **Decision**: Use Java 21 / Spring Boot for all backend services, including ML/AI wrappers.
* **Context**: Original concept utilized Python/FastAPI for ML, leading to a complex multi-language microservice footprint.
* **Reason**: Unifies the tech stack, simplifies deployment, and fits Capstone constraints.
* **Consequences**: Must utilize Java-compatible ML libraries (e.g., Tribuo, DJL) instead of standard Python stacks (Scikit-Learn/PyTorch).

## ADR-002 — React frontend
* **Decision**: Use React (Vite, TypeScript, Tailwind).
* **Context**: Need a responsive, modern SPA framework.
* **Reason**: Industry standard, vast ecosystem, excellent component libraries (shadcn).

## ADR-003 — Supabase PostgreSQL
* **Decision**: Use managed Supabase PostgreSQL as the primary datastore.
* **Context**: Need a robust relational database.
* **Reason**: Free tier availability, excellent pgvector support out-of-the-box.

## ADR-004 — pgvector for RAG
* **Decision**: Use PostgreSQL with `pgvector` instead of a standalone vector database (e.g., Pinecone/Milvus).
* **Context**: RAG requires vector similarity search.
* **Reason**: Reduces infrastructure complexity by keeping vectors in the same database as application data.

## ADR-005 — Spring AI
* **Decision**: Use Spring AI for AI orchestration.
* **Context**: Need an abstraction layer over LLMs (OpenAI, Ollama).
* **Reason**: Native Spring Boot integration, avoids writing custom API clients for every LLM provider.

## ADR-006 — Anonymous session model
* **Decision**: Use Redis-backed UUID cookies instead of JWT / Email registration.
* **Context**: Security tool users often prefer low-friction, anonymous usage.
* **Reason**: Maximizes ease of use.
* **Consequences**: Data is tied strictly to the browser session.

## ADR-007 — Redis for sessions/cache
* **Decision**: Use Redis for ephemeral state.
* **Reason**: Fast TTL management for sessions and rate limiting.

## ADR-008 — RabbitMQ for asynchronous processing
* **Decision**: Use RabbitMQ for background jobs.
* **Context**: Event log parsing and report generation are slow and will block HTTP threads.
* **Reason**: Reliable message delivery, standard Spring Boot integration.
