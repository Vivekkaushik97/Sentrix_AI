# Phase 19: Enterprise Security Operations Model

## Overview
Defined the unified operational pipeline converging Intelligence, Hunting, and Remediation.

## Orchestration Flow
1. **Intelligence & Hunting:** Threat feeds and Hunt queries surface factual IOCs and Findings.
2. **Context & Correlation:** These findings map to `SecurityContext` nodes, connecting to `security_incidents`.
3. **Risk Aggregation:** The system calculates a unified `aggregated_risk_score` (0-100).
4. **Operations Queue:** High-risk entities populate the Analyst Queue.
5. **Action Recommendation:** Deterministic rules propose mitigation actions.
6. **ApprovalGate:** Human analysts review and approve/reject the proposed actions.
7. **Execution:** The controlled backend executes the approved task.

## Determinism
At no point does the AI hallucinate a risk score or autonomously bypass the ApprovalGate to execute a mitigation.
