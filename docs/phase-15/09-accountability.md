# Phase 15: Accountability Enhancements

## Overview
Extended the `enterprise_audit_logs` introduced in Phase 14 to cover all new compliance and governance capabilities.

## Audited Actions
- **Compliance Operations:** Framework creation, control updates, control assessment state changes (e.g., transitioning from `NOT_ASSESSED` to `PASS`).
- **Evidence Management:** Uploading/attaching evidence, and particularly mapping evidence to specific controls.
- **Policy Modifications:** Any changes to `security_policies` are logged with the full delta (before and after state metadata where applicable).

## Safety
- Audit logs remain immutable via application constraints.
- No sensitive configuration data or secrets are recorded in the JSONB metadata field.
