# Dashboard API

## Base Path: `/api/v1/dashboard`

Optimized aggregation for the main UI.

## Endpoints

### `GET /api/v1/dashboard/summary`
* **Purpose**: Fetch high-level statistics without exposing raw DB structures.
* **Response**: `200 OK`
  ```json
  {
    "data": {
      "totalAnalyses": 45,
      "riskDistribution": {
        "CRITICAL": 2,
        "HIGH": 5,
        "MEDIUM": 10,
        "LOW": 28
      },
      "recentActivity": [
        // Top 5 analysis_records objects
      ]
    }
  }
  ```
* **Performance**: Backed by a single optimized JPA projection/DTO query.
