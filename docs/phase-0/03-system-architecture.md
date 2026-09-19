# System Architecture

The Sentrix AI system utilizes a modern, decoupled architecture driven by a React frontend and a Java 21 Spring Boot backend.

```
                     SENTRIX AI
                         |
                         v
              React + TypeScript (Vite, Tailwind)
                         |
                       Axios
                         |
                         v
              Java 21 + Spring Boot (REST APIs)
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
      data
                         |
                         v
                   Spring AI
                         |
                         v
                  Configurable LLM
```

### Request Flow
1. User interacts with the React frontend.
2. Frontend makes HTTP requests via Axios to the Spring Boot REST API.
3. Spring Security validates the session via Redis.
4. Controller routes the request to the appropriate Domain Service (e.g., `FraudDetectionService`).

### Data Flow
1. **Synchronous**: Small payload requests (e.g., CVE lookup) query Supabase PostgreSQL directly, optionally format context using Spring AI, and return the response immediately.
2. **Asynchronous**: Heavy operations (e.g., large Event Log parsing, Report Generation) place a job in RabbitMQ. The backend returns a `jobId`. Worker services consume the message, process the data, persist to PostgreSQL, and update the status in Redis/PG. Frontend polls or receives notifications (TO BE DECIDED) for completion.

### AI Flow
1. Specialized modules construct context from DB queries.
2. The context and user intent are passed to the `ai` module.
3. Spring AI formats the prompt and calls the Configurable LLM provider.
4. Output is validated, persisted (if necessary), and returned.

### RAG Flow
1. **Ingestion**: Admin uploads cybersecurity documents -> text is extracted -> chunked -> embedded via Spring AI -> stored in Supabase PostgreSQL using `pgvector`.
2. **Retrieval**: User asks question -> query is embedded -> similarity search runs against pgvector -> top-K chunks are injected into the LLM prompt -> AI responds.

### Session Flow
1. Client connects without authentication.
2. Backend generates a UUID session token.
3. Token stored in Redis with an expiration (TTL).
4. Token returned to client as an HTTP-only, SameSite=Strict cookie.
5. Every subsequent request extends the TTL in Redis.

### Future Phases
Docker, Nginx (Reverse Proxy), Cloudflare (Edge Security/WAF), and Prometheus/Grafana (Monitoring) will wrap this core architecture in later deployment phases.
