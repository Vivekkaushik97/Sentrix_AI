# API Contract

## Conventions
* **Base URL**: `/api/v1`
* **Content-Type**: `application/json`
* **Authentication**: HTTP-only Cookie (`SESSION_ID`) for state. No Authorization headers required for anonymous flows.
* **Pagination**: `?page=0&size=20&sort=createdAt,desc`
* **Idempotency**: `POST` operations that create resources should return HTTP 201. Identical `POST` requests create new resources. `PUT`/`PATCH` are idempotent.

## Endpoint Groups (Proposed)

### 1. Health & Session
* `GET /api/v1/health` - System status.
* `POST /api/v1/session/init` - Initialize an anonymous session (Set-Cookie).
* `GET /api/v1/session/status` - Validate active session.

### 2. Dashboard
* `GET /api/v1/dashboard/summary` - Aggregate metrics for the session.

### 3. Analyses (Common)
* `GET /api/v1/analyses` - Paginated history of all analyses for the session.
* `GET /api/v1/analyses/{id}` - Get status/details of a specific analysis.

### 4. Fraud Detection
* `POST /api/v1/fraud/analyze`
  * **Request**: JSON containing transaction details.
  * **Response**: Fraud probability, Risk score, AI explanation. (Synchronous).

### 5. Event Logs
* `POST /api/v1/event-logs/upload`
  * **Request**: `multipart/form-data` with `.evtx` or `.xml` file.
  * **Response**: `202 Accepted` with `jobId`. (Asynchronous processing).
* `GET /api/v1/event-logs/jobs/{jobId}` - Poll status.
* `GET /api/v1/event-logs/{analysisId}` - Retrieve completed analysis and threat indicators.

### 6. CVE Intelligence
* `GET /api/v1/cves/{cveId}`
  * **Response**: CVE details and AI explanation. (Synchronous).
* `POST /api/v1/cves/search`
  * **Request**: JSON with search criteria.

### 7. AI Cybersecurity Assistant (Chat)
* `POST /api/v1/chat/sessions` - Create a new chat thread.
* `GET /api/v1/chat/sessions/{sessionId}/messages` - History.
* `POST /api/v1/chat/sessions/{sessionId}/messages`
  * **Request**: `{ "content": "user query" }`
  * **Response**: AI reply.

### 8. RAG Management (Admin/Internal)
* `POST /api/v1/rag/documents` - Ingest new knowledge.
* `GET /api/v1/rag/search` - Test similarity search.

### 9. Reports
* `POST /api/v1/reports/generate`
  * **Request**: JSON list of `analysisIds`.
  * **Response**: `202 Accepted` with `jobId`.
* `GET /api/v1/reports/{reportId}/download` - Stream PDF/HTML.
