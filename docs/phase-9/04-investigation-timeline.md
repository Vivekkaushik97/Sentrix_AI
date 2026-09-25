# Phase 9: Investigation Timeline

## Objective
Construct a chronological representation of a security case based strictly on persisted data.

## Implementation
- **API**: Exposed `GET /api/v1/investigations/{id}/timeline`.
- **Logic**: Aggregates `InvestigationEvent` records (pointers to UPI, Windows, Incidents) and `InvestigationCorrelation` records (explicit deterministic links).
- **Sorting**: The backend performs temporal sorting by `createdAt` prior to sending the JSON response.
- **DTO**: Uses `InvestigationTimelineDto` mapping to ensure no unnecessary JPA fields are exposed, enforcing data boundaries.
