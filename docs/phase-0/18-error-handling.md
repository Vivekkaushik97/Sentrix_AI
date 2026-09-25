# Phase 0: Error Handling Strategy

This document defines the standardized error handling strategy for Sentrix AI to ensure a consistent API contract and prevent information leakage.

## 1. Global Exception Handler
Spring Boot's `@RestControllerAdvice` will be used to intercept all exceptions thrown by controllers or services and format them into a standard JSON response. Internal stack traces or raw database exceptions will NEVER be exposed to the client.

## 2. Standard Error Response Format
Every error returned by the API must conform to this exact structure:

```json
{
  "timestamp": "2023-10-27T10:05:00Z",
  "status": 400,
  "code": "VALIDATION_ERROR",
  "message": "Request validation failed. See details.",
  "path": "/api/v1/fraud/analyze",
  "requestId": "req-uuid-1234",
  "details": [
    {
      "field": "amount",
      "issue": "must be greater than 0"
    }
  ]
}
```

## 3. Error Categories & Codes

### A. Client Errors (HTTP 4xx)
*   **400 Bad Request**:
    *   `VALIDATION_ERROR`: Missing or malformed input (e.g., negative transaction amount, invalid email format).
    *   `MALFORMED_JSON`: Request body cannot be parsed.
*   **401 Unauthorized**:
    *   `SESSION_INVALID`: Session cookie is missing, expired, or invalid.
*   **403 Forbidden**:
    *   `ACCESS_DENIED`: Valid session, but attempting to access another session's data (e.g., fetching an analysis ID that belongs to a different user).
*   **404 Not Found**:
    *   `RESOURCE_NOT_FOUND`: E.g., requested analysis ID does not exist.
*   **413 Payload Too Large**:
    *   `FILE_TOO_LARGE`: Uploaded EVTX file exceeds the configured size limit.
*   **415 Unsupported Media Type**:
    *   `INVALID_FILE_TYPE`: Uploaded file is not a `.evtx` or `.xml`.
*   **429 Too Many Requests**:
    *   `RATE_LIMIT_EXCEEDED`: API quota exceeded.

### B. Server Errors (HTTP 5xx)
*   **500 Internal Server Error**:
    *   `INTERNAL_ERROR`: Catch-all for unexpected failures (NullPointerExceptions, database disconnects). The `message` field should be generic (e.g., "An unexpected error occurred.") and the actual exception logged securely on the backend.
*   **502 Bad Gateway / 503 Service Unavailable**:
    *   `EXTERNAL_API_ERROR`: The external CVE API or LLM provider is down or unresponsive.
    *   `MODEL_INFERENCE_ERROR`: The local Java ML model failed to process the request.
    *   `PROCESSING_ERROR`: A background RabbitMQ task failed unrecoverably.

## 4. Correlation (Request IDs)
Every incoming HTTP request should be assigned a unique `requestId` (UUID) early in the filter chain.
*   This ID must be included in the JSON error response.
*   This ID must be included in all backend logs related to that request (MDC context).
*   *Benefit*: If a user reports an error, they provide the `requestId`, allowing developers to trace the exact sequence of events in the logs without asking for sensitive session info.

## 5. Retries and Idempotency
*   Clients should generally not automatically retry `POST` requests upon receiving a `500` error unless the endpoint is explicitly documented as idempotent.
*   For asynchronous jobs (RabbitMQ), transient errors (e.g., a brief DB timeout) should be retried internally by the worker before marking the job as `FAILED`.
