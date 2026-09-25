# Phase 0: API Contract

This document defines the REST API conventions for Sentrix AI. No application code will be implemented in this phase.

## Global Conventions

* **Base URL**: `/api/v1`
* **Request/Response Format**: JSON (`application/json`)
* **Identifiers**: UUIDs for resource IDs.
* **Authentication**: Cookie-based anonymous session (`SESSION_ID`).
* **Cross-Origin**: CORS configured for allowed frontend origins.
* **Idempotency**: `POST` operations are generally not idempotent, `PUT`/`GET`/`DELETE` are. Use `Idempotency-Key` headers for critical `POST` actions if required later.

## Standard Endpoint Groups

### 1. Session Management
* `POST /api/v1/session/init` - Initialize a new anonymous session. Set HTTP-only cookie.
* `GET /api/v1/session/status` - Check if session is valid.
* `DELETE /api/v1/session/invalidate` - Terminate current session.

### 2. Dashboard
* `GET /api/v1/dashboard/metrics` - Retrieve aggregate statistics (recent threats, system health) for the UI.

### 3. UPI Fraud Detection
* `POST /api/v1/fraud/analyze` - Submit transaction data.
  * *Request Body*: Transaction DTO (**Fields TO BE FINALIZED**)
  * *Response Body*: `AnalysisRecord` DTO with nested `FraudAnalysis` DTO.
* `GET /api/v1/fraud/{id}` - Fetch specific fraud analysis result.

### 4. Windows Event Logs
* `POST /api/v1/event-logs/upload` - Upload an EVTX file (multipart/form-data).
  * *Response Body*: Job ID (for async tracking).
* `GET /api/v1/event-logs/{id}` - Fetch specific log analysis result.
* `GET /api/v1/event-logs/{id}/events` - Fetch paginated parsed events.

### 5. CVE Intelligence
* `POST /api/v1/cves/lookup` - Query a CVE ID and generate AI explanation.
  * *Request Body*: `{ "cveId": "CVE-XXXX-XXXX" }`
  * *Response Body*: `AnalysisRecord` DTO with nested `CveAnalysis` DTO.
* `GET /api/v1/cves/{id}` - Fetch historical CVE analysis.

### 6. AI Cybersecurity Assistant
* `POST /api/v1/chat/sessions` - Start a new chat session.
* `GET /api/v1/chat/sessions` - List user's chat sessions.
* `POST /api/v1/chat/sessions/{sessionId}/messages` - Send a message to the assistant.
  * *Response*: May be standard JSON or Server-Sent Events (SSE) for streaming (**TO BE DECIDED**).
* `GET /api/v1/chat/sessions/{sessionId}/messages` - Get chat history.

### 7. Analysis History
* `GET /api/v1/analyses` - Fetch a paginated list of all past analyses (Fraud, Logs, CVEs) for the current session.
  * *Query Params*: `?page=0&size=20&type=FRAUD`

### 8. Security Reports
* `POST /api/v1/reports/generate` - Request report generation for a specific analysis.
  * *Request Body*: `{ "analysisId": "uuid", "format": "PDF" }`
  * *Response Body*: Job ID.
* `GET /api/v1/reports/{id}/download` - Download the generated report file.

### 9. System Health (Actuator)
* `GET /actuator/health` - Basic health status.

## Standard DTO Structures

### Standard Success Response
Most endpoints will return the domain object directly. For complex operations or paginated responses, standard structures apply.

```json
{
  "content": [ ... ],
  "page": {
    "size": 20,
    "totalElements": 45,
    "totalPages": 3,
    "number": 0
  }
}
```

### Async Job Response
```json
{
  "jobId": "uuid",
  "status": "PENDING",
  "message": "File processing has started."
}
```
