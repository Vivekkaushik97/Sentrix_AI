# Phase 10: Context Resolution Engine

## Logic
`ContextResolutionService` deterministically walks the database to resolve context. When queried for an investigation, it queries all explicit `InvestigationCorrelation` entries and maps them into Graph Node and Edge DTOs.
It guarantees absolute zero hallucinations because the relationships are extracted straight from foreign key tables.
