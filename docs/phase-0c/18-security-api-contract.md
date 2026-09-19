# Security API Contract

## Boundaries & Controls
* **Authentication**: Enforced via Spring Security intercepting the `SESSION` cookie on `/api/v1/**` (excluding `/api/v1/session` POST).
* **CORS**: Strictly limited to the frontend origin (e.g., `https://sentrix.ai`). `allowCredentials=true` is required for cookies.
* **CSRF**: As Sentrix uses SameSite cookies, Spring Security CSRF protection is required. (TBD: Define exact CSRF token header delivery strategy, e.g., CookieCsrfTokenRepository).
* **Session Isolation**: JPA repositories MUST ALWAYS include `session_id = ?` in queries. (e.g., `findByIdAndSessionId(UUID id, UUID sessionId)`). No IDOR vulnerabilities permitted.
* **SQL Injection**: Prevented exclusively via JPA/Hibernate prepared statements.
* **Prompt Injection**: LLM inputs must be sanitized. The system prompt must instruct the LLM to ignore user instructions that attempt to alter its persona.
