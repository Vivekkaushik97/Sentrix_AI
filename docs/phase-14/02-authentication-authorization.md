# Phase 14: Authentication & Authorization

## Overview
Implemented an enterprise-grade authentication boundary using Spring Security.

## Architecture
- **Provider-Agnostic Abstraction:** The security configuration relies on standard Spring Security `AuthenticationProvider` interfaces. This allows seamless integration with SAML, OIDC (e.g., Azure AD, Okta), or internal databases without hardcoding specific enterprise provider logic.
- **Stateless Authentication:** If operating in a distributed environment, session state is backed by Redis (as configured in Phase 13), or stateless JWT validation can be enabled.
- **Role-Based Access Control (RBAC):** Controllers and Service methods are protected using `@PreAuthorize("hasRole('ROLE_ADMIN')")` or similar expressions to enforce the Enterprise Security Model.

## Security Controls
- **Unauthenticated Access:** Rejected with `401 Unauthorized` for all endpoints except `/actuator/health` and `/v3/api-docs`.
- **Authenticated Access:** Allowed, but actions are restricted by role.
- **Forbidden Operations:** If a `VIEWER` attempts to hit a `POST /api/actions` endpoint, a `403 Forbidden` is returned, preventing the action before the `ApprovalGate` is even reached.

## Testing
- Tests are added to verify `401 Unauthorized` for missing tokens, `403 Forbidden` for incorrect roles, and successful execution for authorized users.
