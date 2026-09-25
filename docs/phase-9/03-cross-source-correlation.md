# Phase 9: Cross-Source Correlation

## Objective
Enable explicit deterministic references connecting disparate data pieces (UPI, Windows Events, generic incidents) to a single Investigation context without copying the source entity data. 

## Implementation
- **Data Model**: Implemented `InvestigationCorrelation` entity and matching Flyway script `V6__investigation_correlations.sql`.
- **References**: Connects `sourceRecordId` and `sourceRecordType` (e.g. `SecurityIncident` #123) with a `relatedRecordId` and `relatedRecordType` (e.g. `UpiTransaction` #456).
- **Explanation**: A strict requirement of `correlationReason` and `confidenceScore` prevents arbitrary machine linking without explainability. 
- **Endpoint**: Exposed `POST /api/v1/investigations/{id}/correlations`.
