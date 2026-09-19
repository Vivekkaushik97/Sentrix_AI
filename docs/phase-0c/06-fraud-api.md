# Fraud API

## Base Path: `/api/v1/fraud`

Handles UPI Fraud detection analysis.

## Endpoints

### `POST /api/v1/fraud`
* **Purpose**: Submit transaction data for inference.
* **Request**:
  ```json
  {
    "amount": 1000.50,
    "timestamp": "2024-10-01T12:00:00Z",
    "features": { ... } // TBD: Exact dataset variables
  }
  ```
* **Process**: Synchronous (assuming fast ML inference via Java).
* **Response**: `201 Created`
  ```json
  {
    "data": {
      "analysisId": "uuid",
      "status": "COMPLETED",
      "riskScore": 85,
      "riskClassification": "HIGH",
      "fraudProbability": 0.85,
      "aiExplanation": "..."
    }
  }
  ```
* **Database**: Creates `analysis_records`, `fraud_analyses`, and `fraud_transactions`.

### `GET /api/v1/fraud/{analysisId}`
* **Purpose**: Retrieve specific fraud analysis details.
* **Response**: `200 OK` (Detailed view including input features and explanation).
