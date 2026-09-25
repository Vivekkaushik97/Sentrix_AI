# Phase 9: Evidence Architecture

## Objective
Provide deterministic linkage between analyst investigations and source records.

## Architecture
- **InvestigationEvent**: Serves as the `InvestigationEvidence` abstraction.
- **No Data Duplication**: Captures only the `eventType`, `referenceId`, and a brief `summary` of the source (e.g. UPI, Windows Event). The full record is fetched via its native controller when needed, preserving referential integrity.
- **Empty States**: If no evidence exists, the timeline gracefully defaults to an empty state rather than inventing fake data.
