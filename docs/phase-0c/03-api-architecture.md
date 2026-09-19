# API Base Architecture

## Layering Model

1. **Frontend (React/Axios)**: Sends HTTP requests with `withCredentials: true` to include session cookies.
2. **Reverse Proxy (Nginx)**: Handles TLS termination, routes to `/api/v1`.
3. **Spring Security Filter Chain**: Validates the anonymous session cookie. Rejects if missing on secured endpoints.
4. **Spring Web (Controllers)**: Receives HTTP, performs Bean Validation (@Valid), maps DTOs, returns standard envelopes.
5. **Service Layer (Java Domain)**: Orchestrates business logic, manages transactions, interfaces with RabbitMQ/Redis.
6. **Persistence Layer (Spring Data JPA)**: Maps to Supabase PostgreSQL.

## Synchronous vs Asynchronous Operations

### Synchronous (Immediate Response)
* Session creation.
* Dashboard aggregation fetching.
* Chat interactions (unless streaming).
* Analysis History fetching.
* CVE lookup (cache hit).

### Asynchronous (Eventual Consistency)
* **Pattern**: Controller returns `202 Accepted` with a Job/Analysis ID. RabbitMQ worker processes it. Frontend polls `/analyses/{id}` for status `COMPLETED`.
* **Operations**:
  * Event Log (.evtx) parsing.
  * PDF Report Generation.
  * Complex UPI Fraud batch inference (if applicable).
