# Phase 14: User / Analyst Model

## Overview
Introduced a persistent identity model to support role-based operations and attribution across the platform.

## Architecture
- **V10 Migration (`V10__enterprise_security.sql`):** Created the `security_users` and `user_roles` tables. This acts as a mapping from external identity providers (if applicable) or a standalone user directory to internal entity references.
- **Entities:**
  - `SecurityUser`: Represents the authenticated identity (e.g., Jane Doe, `jane.doe@example.com`).
  - `UserRole`: Associates the user with roles defined in the Enterprise Security Model (`ROLE_ADMIN`, `ROLE_SECURITY_ANALYST`, etc.).

## Rationale
While Spring Security can use stateless external identities (e.g., JWT claims), persisting an internal representation allows for foreign-key constraints on incidents, investigations, and audit logs. This guarantees referential integrity when assigning incidents to analysts or tracking who approved an action.
