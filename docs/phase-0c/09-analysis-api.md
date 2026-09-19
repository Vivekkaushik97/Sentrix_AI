# Analysis API

## Base Path: `/api/v1/analyses`

Provides a unified view of `analysis_records`. Crucial for Dashboard and History pages.

## Endpoints

### `GET /api/v1/analyses`
* **Purpose**: Paginated list of all analyses for the current session.
* **Query Params**: `page`, `size`, `type` (FRAUD, EVENT_LOG, CVE), `status`.
* **Response**: `200 OK`
  ```json
  {
    "data": [
      {
        "id": "uuid",
        "type": "FRAUD",
        "status": "COMPLETED",
        "riskScore": 85,
        "riskClassification": "HIGH",
        "createdAt": "..."
      }
    ],
    "meta": { "page": 0, "size": 20, "totalElements": 45, "totalPages": 3 }
  }
  ```

### `DELETE /api/v1/analyses/{id}`
* **Purpose**: Soft/Hard delete an analysis from history.
