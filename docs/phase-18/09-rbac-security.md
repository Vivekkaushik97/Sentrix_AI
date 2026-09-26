# Phase 18: RBAC & Security

## Overview
Mapped Threat Hunting endpoints to existing Phase 14 Authorization matrices.

## Roles
- `THREAT_HUNT_READ`, `THREAT_HUNT_CREATE`, `THREAT_HUNT_UPDATE`, `THREAT_HUNT_EXECUTE`.
- Privileged operations correctly assert `@PreAuthorize` before touching backend layers, preventing IDOR or privilege escalation.
