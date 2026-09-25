# Phase 0: Asynchronous Processing Contract (RabbitMQ)

This document defines the strategy for handling long-running or resource-intensive tasks in Sentrix AI using RabbitMQ.

## 1. Justification for Asynchronous Processing
Synchronous HTTP requests should complete within hundreds of milliseconds. Operations that take seconds or minutes (parsing large files, generating PDFs, heavy ML inference) must not block the HTTP thread. They will be offloaded to RabbitMQ.

## 2. Approved Asynchronous Candidates
*   **Windows Event Log Analysis**: Parsing a 50MB `.evtx` file and evaluating hundreds of rules.
*   **Security Report Generation**: Compiling data and rendering a PDF.
*   **RAG Knowledge Ingestion**: Processing, chunking, and embedding large PDFs/Markdown files for the knowledge base.
*   (Optional) Complex AI explanations if LLM generation time is deemed too slow for synchronous waiting.

## 3. Message Broker Concepts

### Exchanges and Routing
*   Use Direct or Topic exchanges.
*   Example: `sentrix.tasks.exchange`

### Queues
Define specific queues for different workloads to allow independent scaling of workers:
*   `queue.event_log.parse`
*   `queue.report.generate`
*   `queue.rag.ingest`

### Job Payload Structure (JSON)
Messages must contain sufficient context to execute the task, but not large data blobs (pass IDs or file paths instead).

```json
{
  "jobId": "uuid-1234",
  "sessionId": "uuid-5678",
  "taskType": "PARSE_EVTX",
  "payload": {
    "fileId": "uuid-abcd",
    "storagePath": "/tmp/uploads/file.evtx"
  },
  "submittedAt": "2023-10-27T10:00:00Z"
}
```

## 4. Job Tracking Lifecycle
Because the HTTP request returns immediately, the frontend needs a way to know when the job is done.

1.  **Submit**: Controller creates a `jobId`, saves initial status (`PENDING`) to Redis, publishes message to RabbitMQ, and returns `jobId` to client.
2.  **Process**: RabbitMQ Consumer (Worker) receives message, updates status to `PROCESSING` in Redis.
3.  **Execute**: Worker performs the heavy lifting (e.g., parses EVTX, saves results to PostgreSQL).
4.  **Complete**: Worker updates status to `COMPLETED` (or `FAILED`) in Redis, potentially with a reference to the newly created database record ID.
5.  **Client Polling**: Frontend polls `/api/v1/jobs/{jobId}/status` every few seconds until it sees `COMPLETED`, then redirects the user to the result page.

## 5. Resilience & Failure Handling
*   **Dead Letter Exchange (DLX)**: If a message fails processing multiple times (e.g., malformed EVTX file crashes parser), it must be routed to a DLX (`queue.dead_letter`) instead of infinitely retrying and blocking the queue.
*   **Idempotency**: Consumers must be written such that processing the exact same message twice does not corrupt data (e.g., check if `fileId` was already parsed before starting).
*   **Retry Policy**: Implement Spring AMQP retry templates for transient errors (e.g., temporary DB connection loss), but fail fast for unrecoverable errors (e.g., File Not Found).
