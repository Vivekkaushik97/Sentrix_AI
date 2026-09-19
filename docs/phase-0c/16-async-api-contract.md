# Asynchronous API Contract

For heavy operations (Event Logs, Reports), Sentrix AI uses a standard Async Polling Pattern.

1. **Client POSTs Request**.
2. **Backend**:
   * Creates DB record (Status: `PENDING`).
   * Publishes message to RabbitMQ exchange.
   * Responds immediately: `202 Accepted` with the ID.
3. **Client Polling**:
   * Client calls `GET /{resource}/{id}` every X seconds.
   * Backend returns current status (`PROCESSING`, `COMPLETED`, `FAILED`).
4. **Completion**:
   * Worker finishes job, updates DB to `COMPLETED`.
   * Next client poll receives the final payload.

*Future TBD: Consider Server-Sent Events (SSE) or WebSockets if polling causes excessive load.*
