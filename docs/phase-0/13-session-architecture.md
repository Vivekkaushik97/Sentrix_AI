# Anonymous Session Architecture

Sentrix AI is designed for frictionless, anonymous usage. No mandatory email/password registration is required.

## Lifecycle
1. **Creation**: Client accesses the web app. If no session cookie exists, the React app calls `/api/v1/session/init`.
2. **Identification**: Backend generates a secure `UUIDv4`.
3. **Redis Storage**: The UUID is saved in Redis as a key with a configured TTL (e.g., 24 hours). Value can contain rate-limit counters or basic state.
4. **PostgreSQL Storage**: A durable record is created in `anonymous_sessions` for relational integrity (so analyses can belong to a session).
5. **Cookie Delivery**: Backend responds with a `Set-Cookie` header: `SESSION_ID=<uuid>; HttpOnly; Secure; SameSite=Strict; Path=/`.
6. **Validation & Renewal**: On every authenticated API request, a Spring Security filter extracts the cookie, checks Redis. If valid, the Redis TTL is reset (rolling session).
7. **Expiration**: If Redis TTL expires, the session is considered dead. The frontend will be denied access and must request a new session, starting fresh.

## Data Isolation
Every query to PostgreSQL involving user data (analyses, history, chat) MUST include a `WHERE session_id = ?` clause utilizing the ID extracted from the secure HTTP-only cookie.
