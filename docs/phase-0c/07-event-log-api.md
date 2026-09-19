# Event Log API

## Base Path: `/api/v1/event-logs`

Handles EVTX analysis. This is a heavy, asynchronous process.

## Endpoints

### `POST /api/v1/event-logs/upload`
* **Purpose**: Upload an EVTX file.
* **Content-Type**: `multipart/form-data`
* **Process**: Saves file locally/S3. Creates `uploaded_files` and `analysis_records` (Status: PENDING). Dispatches RabbitMQ job.
* **Response**: `202 Accepted`
  ```json
  {
    "data": {
      "analysisId": "uuid",
      "status": "PENDING",
      "message": "Analysis queued for processing."
    }
  }
  ```

### `GET /api/v1/event-logs/{analysisId}`
* **Purpose**: Poll status / Retrieve completed results.
* **Response**: `200 OK`
  ```json
  {
    "data": {
      "status": "COMPLETED",
      "totalEvents": 50000,
      "suspiciousEvents": 12,
      "aiSummary": "...",
      "threatIndicators": [
        { "eventId": "4625", "severity": "HIGH", "description": "Failed logon" }
      ]
    }
  }
  ```

*Note: Object storage provider for temp raw logs is TBD.*
