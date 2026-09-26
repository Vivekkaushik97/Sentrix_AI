# Phase 18: Analyst Notes & Collaboration

## Overview
Introduced persistent, auditable annotation structures for human-driven hunts.

## Structure
- `threat_hunt_notes` associates rich-text insights directly with a Hunt or a specific Finding.
- **RBAC Enforcement:** Authorship maps directly to `security_users` via Phase 14 RBAC. Analysts cannot impersonate others, and modifications are strictly audited, ensuring non-repudiation.
