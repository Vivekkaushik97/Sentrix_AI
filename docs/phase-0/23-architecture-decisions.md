# Phase 0: Architectural Decision Record (ADR)

This document records the major architectural decisions made during Phase 0 for Sentrix AI.

## ADR-001: Java-centric Architecture
*   **Decision**: Replace the originally proposed Python/FastAPI ML service with a unified Java 25 LTS / Spring Boot architecture.
*   **Context**: The team evaluated the complexity of maintaining a polyglot microservice architecture (Python + Java) versus a unified ecosystem.
*   **Reason**: Simplifies deployment, reduces operational overhead, unifies the domain model, and leverages Spring Boot's robust enterprise features for security and API management.
*   **Consequences**: Requires finding Java-compatible ML inference libraries (like Tribuo or DJL) instead of standard Python tooling (scikit-learn, pandas).
*   **Alternatives considered**: Python/FastAPI microservice communicating via HTTP/gRPC. Rejected due to deployment complexity for this project scope.

## ADR-002: React Frontend
*   **Decision**: Use React, Vite, and TypeScript for the frontend UI.
*   **Context**: A modern, responsive, and highly interactive UI is required.
*   **Reason**: React provides a massive ecosystem (Tailwind, shadcn/ui, Recharts) that perfectly aligns with the requirement for a "premium cybersecurity SaaS" aesthetic.
*   **Consequences**: Requires maintaining separate frontend and backend codebases and establishing strict REST API contracts.
*   **Alternatives considered**: Server-side rendering (Thymeleaf). Rejected because it cannot easily provide the required dynamic, SPA-like user experience.

## ADR-003: Supabase PostgreSQL
*   **Decision**: Use Supabase as the managed PostgreSQL database provider.
*   **Context**: The application requires a robust relational database.
*   **Reason**: Supabase provides a generous free tier, managed PostgreSQL, and native support for the `pgvector` extension required for RAG.
*   **Consequences**: Ties the data layer to PostgreSQL specific features (which is acceptable).
*   **Alternatives considered**: MySQL, MongoDB. Rejected because they lack native vector search integration on par with pgvector.

## ADR-004: pgvector for RAG
*   **Decision**: Use the `pgvector` extension within PostgreSQL to store and query knowledge base embeddings.
*   **Context**: The RAG system needs a vector database to perform similarity searches on document chunks.
*   **Reason**: Keeps the infrastructure consolidated. We avoid standing up a separate, dedicated vector database (like Pinecone or Milvus) by using our existing PostgreSQL instance.
*   **Consequences**: Requires managing vector indexes in PostgreSQL.
*   **Alternatives considered**: Pinecone, Milvus, Qdrant. Rejected to reduce infrastructure sprawl.

## ADR-005: Spring AI
*   **Decision**: Use the Spring AI framework for all LLM and embedding integrations.
*   **Context**: The application needs to interact with AI models for explanations and chatbots, but we want to avoid vendor lock-in.
*   **Reason**: Spring AI provides a unified abstraction layer over multiple providers (OpenAI, Anthropic, Ollama). We can switch providers simply by changing configuration properties.
*   **Consequences**: We are bound by the abstraction capabilities of Spring AI.
*   **Alternatives considered**: Direct REST calls to OpenAI. Rejected due to vendor lock-in. LangChain4j. Evaluated, but Spring AI provides tighter integration with the Spring Boot ecosystem.

## ADR-006: Anonymous Session Model
*   **Decision**: Use an anonymous, cookie-based session model without requiring email/password registration.
*   **Context**: The goal is to provide a frictionless user experience while still maintaining data isolation between users.
*   **Reason**: Lowers the barrier to entry. Security is maintained by treating the secure `SESSION_ID` cookie as the identity token.
*   **Consequences**: If a user clears their cookies or the session expires, they lose access to their history. This is an accepted trade-off for this platform concept.
*   **Alternatives considered**: JWT with OAuth2/OIDC. Rejected as overly complex for a platform that doesn't explicitly require persistent user profiles across devices.

## ADR-007: Redis for Sessions and Cache
*   **Decision**: Use Redis for Spring Session management and temporary caching (e.g., CVE API responses, Job Status).
*   **Context**: Fast, ephemeral storage is needed for sessions and preventing API rate limits.
*   **Reason**: Redis is the industry standard for this use case, integrating seamlessly with Spring Session.
*   **Consequences**: Introduces another infrastructure dependency.
*   **Alternatives considered**: In-memory caching (Caffeine). Rejected because it does not scale horizontally if the application is deployed across multiple instances.

## ADR-008: RabbitMQ for Asynchronous Processing
*   **Decision**: Use RabbitMQ for handling long-running background tasks (EVTX parsing, PDF generation).
*   **Context**: Parsing 50MB EVTX files synchronously blocks HTTP threads and leads to timeouts.
*   **Reason**: RabbitMQ provides robust message queuing, enabling horizontal scaling of worker processes and preventing HTTP timeouts.
*   **Consequences**: Adds significant architectural complexity (Broker, Exchanges, Queues, Consumers).
*   **Alternatives considered**: Spring `@Async` (Thread Pools). Rejected because it is entirely in-memory; if the server crashes, all pending jobs are lost. Kafka. Rejected as overkill for simple task queuing.
