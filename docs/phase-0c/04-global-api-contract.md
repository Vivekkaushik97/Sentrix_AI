# Global API Contract

## Response Envelope
To simplify frontend parsing, ALL successful API responses (except raw file downloads) will be wrapped in a consistent envelope:

```json
{
  "data": { ... }, // Or [] for lists
  "meta": {        // Included only for paginated lists
    "page": 0,
    "size": 20,
    "totalElements": 100,
    "totalPages": 5
  }
}
```

## Error Envelope
All API errors will return a standard format. Stack traces are strictly forbidden.

```json
{
  "timestamp": "2024-10-01T12:00:00Z",
  "status": 400,
  "code": "VALIDATION_ERROR",
  "message": "Input validation failed",
  "path": "/api/v1/fraud",
  "requestId": "abc-123",
  "details": [
    { "field": "amount", "issue": "Must be greater than 0" }
  ]
}
```

## Request Conventions
* **Headers**: 
  * `Content-Type: application/json`
  * `Accept: application/json`
* **Session**: Exclusively handled via HTTP-Only cookies. No `Authorization: Bearer` headers for frontend clients.
