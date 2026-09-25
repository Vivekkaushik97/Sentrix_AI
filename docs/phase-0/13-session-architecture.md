# Phase 0: Anonymous Session Architecture

Sentrix AI is designed as a frictionless platform. It employs an anonymous session model rather than mandatory email/password registration. This document outlines how state and identity are maintained.

## 1. Core Concept
Users interact with the platform without creating an account. The system tracks their activity, analysis history, and chat sessions using a unique Session ID stored in a secure, HTTP-only cookie.

## 2. Session Lifecycle
1. **Creation**:
    * A user visits the frontend application.
    * The frontend detects the absence of a `SESSION_ID` cookie.
    * It calls `POST /api/v1/session/init`.
    * The backend generates a Type 4 UUID (`session_id`), creates an `anonymous_sessions` record (in Redis for fast access, and potentially PostgreSQL for durable foreign keys), and sets an HTTP-only cookie in the response.
2. **Identification**:
    * Subsequent API requests from the frontend automatically include the `SESSION_ID` cookie.
    * Spring Security intercepts the request, reads the cookie, and populates the `SecurityContext` with the anonymous user's identity.
3. **Renewal/Expiration**:
    * Sessions have a defined time-to-live (TTL), e.g., 7 days of inactivity.
    * The TTL is refreshed in Redis upon user activity.
    * If the TTL expires, the Redis record is deleted. The user's durable data (analyses) remains in PostgreSQL but becomes "orphaned" (inaccessible to new sessions).
4. **Invalidation**:
    * A user can explicitly choose to "Clear Session" in the UI, which calls `DELETE /api/v1/session/invalidate`, clearing the Redis cache and instructing the browser to delete the cookie.

## 3. Data Isolation
All persistent records (Fraud Analyses, EVTX Uploads, Chat Sessions) must have a `session_id` foreign key.

*   **Rule**: A controller must NEVER return data belonging to a `session_id` that does not match the currently authenticated `SecurityContext`.
*   **Implementation**: Repository queries must always include `WHERE session_id = :currentSessionId`.

## 4. Security Configuration (Cookies)
To prevent session hijacking and cross-site scripting (XSS):

*   **HttpOnly**: `true` (Cannot be read by client-side JavaScript).
*   **Secure**: `true` (Only transmitted over HTTPS).
*   **SameSite**: `Lax` or `Strict` (Prevents CSRF attacks; `Strict` preferred if frontend and backend share a domain/subdomain).

## 5. Technology Stack
*   **Spring Session**: Manages the HTTP Session abstraction.
*   **Redis**: Stores the active session data, handling TTL automatically.
*   **Spring Security**: Integrates with Spring Session to provide `SecurityContextHolder` access to the anonymous principal.
