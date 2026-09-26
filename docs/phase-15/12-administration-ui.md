# Phase 15: Administration UI

## Overview
Added a controlled `/administration` route in the frontend to manage platform maturity features.

## Sections
- **Compliance Frameworks:** Manage imported frameworks and controls.
- **Policies:** View and edit JSON-based system policies (e.g., Retention).
- **Audit Logs:** Read-only view of the `enterprise_audit_logs`.

## Security Constraints
- The `/administration` route and its sub-components are protected. They check if the user holds the `ADMIN` role. 
- Even if a user bypasses the UI check, the backend APIs enforce `@PreAuthorize("hasRole('ADMIN')")`, returning `403 Forbidden`.
- No sensitive credentials or secrets are exposed in this UI.
