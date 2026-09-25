# Phase 6: API Definitions

## Endpoints

### `POST /api/v1/windows-events/ingest`
Accepts batches of normalized JSON Windows events originating from an external collector.

**Request:**
```json
{
  "source": "WIN_COLLECTOR_PS1",
  "computerName": "DESKTOP-HR",
  "events": [
    {
      "timestamp": "2023-10-27T10:00:00Z",
      "logName": "Security",
      "eventId": 4625,
      "user": "Administrator"
    }
  ]
}
```

**Response:**
```json
{
  "status": "SUCCESS",
  "message": "Windows events ingested and analyzed",
  "data": {
    "analysisId": "uuid",
    "eventsAnalyzed": 1,
    "riskScore": 30,
    "detectionCount": 1,
    "correlationCount": 0,
    "status": "COMPLETED"
  }
}
```

## Resilience
- Utilizes Spring's `@Valid` annotation to block improperly formatted payloads before they reach the controller.
- Limits array size to 1000 to prevent Denial of Service via memory exhaustion.
