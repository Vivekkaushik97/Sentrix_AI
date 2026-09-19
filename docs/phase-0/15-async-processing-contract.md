# Asynchronous Processing Contract (RabbitMQ)

To ensure the REST API remains responsive, heavy workloads are offloaded to background workers using RabbitMQ.

## Async Candidates
* Large Windows Event Log parsing and analysis.
* Heavy AI generation tasks (e.g., generating a massive security report).
* RAG knowledge base ingestion and chunking.

## Conceptual Architecture
1. **Controller**: Receives request, saves a `PENDING` job record in PostgreSQL, generates a `jobId`.
2. **Producer**: Publishes a JSON payload to a RabbitMQ Exchange with a specific Routing Key (e.g., `eventlog.analyze`).
3. **Response**: Returns `HTTP 202 Accepted` and the `jobId` to the client.
4. **Consumer**: Spring Boot `@RabbitListener` worker picks up the message, performs the heavy lifting, updates the PostgreSQL record to `COMPLETED` or `FAILED`.

## Messaging Standards
* **Job Payload**: Must include `jobId`, `sessionId`, and necessary identifiers (e.g., `fileId`). Do not pass large binary files through the message broker; pass the storage reference.
* **Idempotency**: Workers must check the job status in PostgreSQL before processing to avoid duplicate work if a message is redelivered.
* **Failure Handling**: Failed messages should be retried (with backoff). If they fail repeatedly, they must be routed to a Dead Letter Queue (DLQ).

*Note: RabbitMQ implementation is deferred to Phase 12.*
