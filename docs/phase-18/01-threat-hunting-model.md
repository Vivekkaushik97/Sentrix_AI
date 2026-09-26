# Phase 18: Threat Hunting Model

## Overview
Defined the deterministic model for analyst-driven, chronological threat hunts.

## Model Schema
- `ThreatHunt`: Represents the lifecycle of a human-driven hunt. States map to `DRAFT`, `READY`, `RUNNING`, `COMPLETED`, `CANCELLED`, `FAILED`.
- `ThreatHuntFinding`: Represents the outcome of an executed hunt query intersecting with existing platform data.
- **Constraints:** This does NOT represent an autonomous scanner. The `created_by_id` explicitly references a human analyst in `security_users` (Phase 14 RBAC).
