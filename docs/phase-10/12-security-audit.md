# Phase 10: Security and No-Fake-Data Audit

## Data Provenance
- `ContextResolutionService` builds Nodes/Edges explicitly from SQL `InvestigationCorrelation` table.
- At no point are artificial threats, simulated risk graphs, or fabricated mock-data node arrays sent to the UI. If correlations are empty, the Graph renders a text fallback.

## Boundary Enforcement
- We did **NOT** introduce EDR-style endpoint quarantines, process-killing, or account freezing actions.
- The system remains a read-only **Intelligence Platform** prioritizing human oversight.
- Risk scores continue to be driven strictly by the rules engine (`IncidentEngine`, `FraudRules`), rather than delegating calculations to the LLM.
