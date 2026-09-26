# Phase 18: Auditability

## Overview
Ensured hunts leave comprehensive enterprise footprints.

## Audit Trails
- Hunt state transitions (`DRAFT` -> `RUNNING` -> `COMPLETED`) are emitted directly into `enterprise_audit_logs`.
- Note creation and finding interactions log the specific Phase 14 Actor ID to prevent repudiation. No sensitive metadata or credentials are leaked in these logs.
