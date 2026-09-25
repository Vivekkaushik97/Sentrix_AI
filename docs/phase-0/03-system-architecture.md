# Phase 0: System Architecture

This document outlines the intended system architecture for Sentrix AI, utilizing a modern, Java-centric backend combined with a React frontend.

## Conceptual Architecture Diagram

```
                     SENTRIX AI
                         |
                         v
              React + TypeScript (Vite, Tailwind)
                         |
                       Axios
                         |
                         v
              Java 25 LTS + Spring Boot (REST API)
                         |
   ------------------------------------------------
   |          |          |         |              |
   v          v          v         v              v
Fraud     Event Log    CVE       AI/RAG       Reports
Engine    Analyzer    Module     Engine        Engine
   |          |          |         |              |
   ------------------------------------------------
                         |
          --------------------------------
          |              |               |
          v              v               v
    Supabase PG       Redis          RabbitMQ
    + pgvector       Sessions        Background
    + application    + cache         Processing
      data           + jobs
                         |
                         v
                   Spring AI
                         |
                         v
            Configurable LLM Provider
```

## 1. Request Flow
1. User accesses the frontend application.
2. Frontend requests `/api/v1/session` to establish an anonymous identity if one does not exist.
3. User performs an action (e.g., uploads an EVTX file).
4. Frontend makes an API call to the appropriate Spring Boot controller.
5. Controller validates input and delegates to a Service layer.
6. Service layer interacts with the Database, Redis, or external APIs.
7. Service layer returns standard DTOs.
8. Controller wraps DTOs in a standard API response and returns JSON to the frontend.

## 2. Data Flow
* **Transactional Data**: Persisted directly to Supabase PostgreSQL using Hibernate/JPA.
* **Session Data**: Stored in Redis for fast access and automatic expiration.
* **File Uploads**: Stored temporarily on disk/storage, metadata in PostgreSQL.
* **AI Prompts**: Assembled in memory using data from PostgreSQL, sent to external LLM, and results stored back in PostgreSQL.

## 3. Service Responsibilities
* **Controllers**: HTTP routing, request validation, response wrapping.
* **Services**: Core business logic, transaction boundaries (`@Transactional`), orchestrating multiple repositories.
* **Repositories**: Spring Data JPA interfaces for database access.
* **Message Consumers**: RabbitMQ listeners for background tasks (e.g., parsing large files, generating PDF reports).

## 4. Synchronous vs Asynchronous Operations
### Synchronous (Immediate Response)
* Session creation/validation
* Dashboard metric retrieval
* UPI Fraud detection (assuming fast ML inference)
* CVE lookups
* Chatbot interactions (with streaming)
* Analysis history retrieval

### Asynchronous (Deferred Processing via RabbitMQ)
* Windows Event Log (EVTX) parsing and analysis (large files)
* PDF Security Report generation
* Background ingestion of RAG knowledge documents

## 5. External API Interactions
* **LLM Provider**: Outbound HTTP requests handled via Spring AI framework.
* **CVE Data Source**: Outbound HTTP requests to external vulnerability databases (e.g., NVD). Needs resilient circuit breakers (e.g., Resilience4j) and caching to handle rate limits.

## 6. AI Flow
1. Domain Service identifies a need for explanation (e.g., high fraud score).
2. Domain Service constructs a specific context object.
3. Context is passed to `AiExplanationService`.
4. Spring AI formats a prompt using the configured model and context.
5. LLM generates a response.
6. Response is parsed and attached to the domain entity (e.g., `FraudAnalysis`).

## 7. RAG Flow
1. **Ingestion**: Admin uploads cybersecurity PDF/Markdown. System extracts text, chunks it, generates embeddings, and saves to PostgreSQL (pgvector).
2. **Retrieval**: User asks the Chat Assistant a question. System embeds the question, queries pgvector for the Top-K most similar chunks.
3. **Augmentation**: System injects those chunks into the LLM prompt.
4. **Generation**: LLM generates an answer grounded in the provided context.

## 8. Database Flow
* **Supabase PostgreSQL**: Acts as the primary durable datastore. Uses HikariCP for connection pooling.
* **pgvector**: Enabled in Supabase for vector similarity search.

## 9. Caching Flow
* **Redis**: Used for caching external API responses (e.g., CVE details) to reduce latency and avoid rate limits. Used for Spring Session management.

## 10. Session Flow
1. Frontend lacks a session cookie.
2. Calls `/api/v1/session/init`.
3. Spring Security + Spring Session intercepts.
4. Redis stores new session metadata.
5. Response includes `Set-Cookie` (HTTP-only, Secure, SameSite).
6. Subsequent requests automatically include the cookie, identifying the anonymous user.

## 11. Report Flow
1. User clicks "Generate PDF Report" for a specific analysis.
2. Controller publishes a message to RabbitMQ.
3. Returns `Job ID` to frontend.
4. RabbitMQ worker picks up message, fetches data, uses PDF library to generate report.
5. Worker saves PDF to storage and updates Job Status in Redis/PostgreSQL.
6. Frontend polls Job Status or receives WebSocket update, then downloads the file.
