# Phase 19: Action Recommendation Engine

## Overview
Connected threat findings to actionable security responses using deterministic rule sets.

## Workflow
- **Rule Example:** If an incident has an `aggregated_risk_score` > 80 and involves a `CONFIRMED` malicious IP, the engine generates a `PROPOSED` action of type `BLOCK_IP`.
- **Enforcement:** The recommendation engine CANNOT bypass the `ApprovalGate`. The action remains dormant in `PROPOSED` status until a human `SECURITY_MANAGER` or `ADMIN` reviews and approves it.
