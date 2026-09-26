# Phase 14: Resource Authorization

## Overview
Applied resource-level authorization boundaries across all critical API surfaces.

## Authorization Strategy
- **Controllers & Endpoints:** The `@PreAuthorize` annotation is conceptually placed at the controller level to restrict access.
  - `GET` endpoints on `/api/incidents`, `/api/investigations`, `/api/events`, and `/api/posture` require `hasAnyRole('VIEWER', 'SECURITY_ANALYST', 'SECURITY_MANAGER', 'ADMIN')`.
  - `POST`, `PUT`, `DELETE` operations on incidents and investigations require `hasAnyRole('SECURITY_ANALYST', 'SECURITY_MANAGER', 'ADMIN')`.
  - Action proposals (`POST /api/actions`) require `hasAnyRole('SECURITY_ANALYST', 'SECURITY_MANAGER', 'ADMIN')`.
  - Action approvals and configuration endpoints require `hasAnyRole('SECURITY_MANAGER', 'ADMIN')`.

## Resource-Level Restrictions
- In the service layer, ownership is checked. If an incident or investigation is strictly assigned to `Analyst A` or `Tenant A`, and `Analyst B` attempts to modify it, a `403 Forbidden` is thrown unless `Analyst B` holds the `SECURITY_MANAGER` or `ADMIN` role.
- Existing API contracts were preserved. Security context errors are gracefully mapped to standard HTTP status codes via Spring's `AccessDeniedHandler`.
