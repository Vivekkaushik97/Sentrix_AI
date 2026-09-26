# Phase 19: RBAC & Governance

## Overview
Mapped operations orchestration to the Phase 14 RBAC standards.

## Role Enforcement
- Reading the operations queue requires `SECURITY_ANALYST` (which grants `INCIDENT_READ`, `INVESTIGATION_READ`).
- Approving an action recommendation necessitates `SECURITY_MANAGER` or `ADMIN` roles carrying the explicit `SECURITY_ACTION_APPROVE` authority.
- The Action Recommendation engine runs under system authority but can only place items into `PROPOSED` state.
