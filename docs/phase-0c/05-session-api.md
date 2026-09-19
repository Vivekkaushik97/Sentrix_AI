# Session API

## Base Path: `/api/v1/session`

The application uses Anonymous Sessions. No email/password is required.

## Endpoints

### `POST /api/v1/session`
* **Purpose**: Initialize a new anonymous session.
* **Action**: Spring Session creates a new UUID in Redis and sets the `SESSION` HTTP-Only cookie. Creates an `anonymous_sessions` row in PostgreSQL.
* **Response**: `201 Created`
  ```json
  { "data": { "sessionId": "uuid", "createdAt": "..." } }
  ```

### `GET /api/v1/session/me`
* **Purpose**: Verify session validity.
* **Response**: `200 OK` (if valid), `401 Unauthorized` (if expired/missing).

### `DELETE /api/v1/session`
* **Purpose**: End the session manually (logout equivalent).
* **Action**: Invalidates Spring Session, clears cookie.

## Behaviors
* **Cookie**: `SameSite=Strict`, `Secure=true`, `HttpOnly=true`.
* **Missing Cookie**: Spring Security returns `401 Unauthorized`. Frontend redirects to an onboarding/initialization screen.
* **Expiration**: Managed by Redis TTL. A PostgreSQL cron job deletes expired rows based on `last_active_at`.
