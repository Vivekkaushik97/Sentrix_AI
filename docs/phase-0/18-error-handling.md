# Error Handling Strategy

Sentrix AI requires a unified, secure approach to API error responses.

## Global Error Response Format
All REST controllers must return errors in this consistent JSON structure:

```json
{
  "timestamp": "2024-05-20T14:32:00Z",
  "status": 400,
  "code": "VALIDATION_ERROR",
  "message": "Input validation failed for transaction amount.",
  "path": "/api/v1/fraud/analyze",
  "requestId": "req-12345678"
}
```

## Standard Error Codes
* `VALIDATION_ERROR` (400) - Bad input data.
* `UNAUTHORIZED_SESSION` (401) - Missing or expired session cookie.
* `NOT_FOUND` (404) - Resource (e.g., CVE ID, Analysis ID) does not exist.
* `RATE_LIMIT_EXCEEDED` (429) - Too many requests.
* `AI_PROVIDER_ERROR` (502) - LLM or Embedding API failed.
* `INTERNAL_SERVER_ERROR` (500) - Unexpected backend crash.

## Implementation Rules
1. Implement via Spring `@RestControllerAdvice`.
2. **Never expose stack traces** or internal framework details in the `message` field.
3. Generate a `requestId` (Correlation ID) for every request, include it in the logs, and return it in the error response to allow tracing bugs.
4. Database connection errors must be masked as generic `INTERNAL_SERVER_ERROR`.
