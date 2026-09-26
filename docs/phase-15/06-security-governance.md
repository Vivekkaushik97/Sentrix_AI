# Phase 15: Security Governance Policies

## Overview
Introduced structured, deterministic platform security policies that dictate core operational behaviors.

## Implementation
- **Schema (`security_policies`):** Stores JSONB documents representing dynamic rules.
- **Example Policies:**
  - `ACTION_APPROVAL_TTL`: Dictates how many hours a proposed action lives before expiring.
  - `RETENTION_AUDIT_DAYS`: Dictates how long audit logs are kept before archivel/purge.
- **Restrictions:** AI cannot modify policies. Policy changes strictly require `ROLE_ADMIN` and generate an immutable audit log.
