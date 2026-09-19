# Report API

## Base Path: `/api/v1/reports`

Handles asynchronous PDF generation.

## Endpoints

### `POST /api/v1/reports`
* **Purpose**: Request a summary PDF.
* **Process**: Returns `202 Accepted`. Dispatches RabbitMQ job.
* **Request**: `{ "timeframeDays": 7 }` (TBD)
* **Response**: `{ "data": { "reportId": "uuid", "status": "PENDING" } }`

### `GET /api/v1/reports`
* **Purpose**: List generated reports.

### `GET /api/v1/reports/{reportId}`
* **Purpose**: Check status / get metadata.

### `GET /api/v1/reports/{reportId}/download`
* **Purpose**: Returns the raw PDF binary.
* **Response**: `200 OK`, `Content-Type: application/pdf`, `Content-Disposition: attachment`.
