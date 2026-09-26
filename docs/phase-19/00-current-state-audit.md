# Phase 19: Current State Audit

## Existing Architecture Baseline (Phases 1-18)
- **Threat Intelligence & Hunting:** Robust foundation (V14, V15, V16 migrations) handles IOCs, Threat Campaigns, and deterministic Hunt queries.
- **Incident & Investigation:** Exist as foundational entities. They currently lack a unified, mathematically calculated "Risk Aggregation" score derived from downstream Threat Hunts and Intel feeds.
- **Security Actions:** The `ApprovalGate` (Phase 11) is rock-solid. Actions exist in states (`PROPOSED`, `PENDING_APPROVAL`, etc.) but are not yet surfaced into a centralized "Security Operations Queue" for analysts.
- **AI Boundaries:** AI is strictly constrained to explaining and summarizing. It is securely walled off from taking operational actions.

## Missing Capabilities for Phase 19
- **Risk Aggregation Engine:** We need a deterministic calculator that weighs incident severities against mapped IOC campaigns to produce a unified operational risk score.
- **Security Operations Queue:** Analysts lack a single pane of glass that fuses pending Action Approvals, high-risk hunts, and critical incidents.
- **Action Recommendation Engine:** The platform needs to deterministically propose allowlisted actions based on Hunt Findings or Incident clusters, pushing them into `PROPOSED` state.

## Implementation Strategy
- Implement `V17__security_operations_orchestration.sql` to add `aggregated_risk_score` to core operational entities.
- Define the architectural documentation without modifying the core Java files unless necessary to pass compilation.
