# Security Architecture Contract

## Transport & Edge
* All communication must occur over HTTPS/TLS.
* Cloudflare (Future Phase) will provide WAF, CDN, and DDoS protection.

## Backend (Spring Security)
* **Sessions**: State managed via UUIDs in Redis. Secure, HTTP-Only, SameSite=Strict cookies prevent XSS exfiltration and CSRF.
* **CORS**: Strictly configured to allow only the specific frontend origin(s). No `*` wildcards.
* **Validation**: All incoming JSON payloads must be validated using Spring Boot Validation (`@Valid`, Hibernate Validator).
* **Exception Handling**: Global `@ControllerAdvice` ensures no stack traces are leaked to the client.

## Database
* **SQL Injection**: Prevented globally by relying on Spring Data JPA and Hibernate parameterized queries. No string concatenation for SQL.
* **Secrets**: Passwords, API keys, and sensitive configs must NEVER be in source code; they must be provided via Environment Variables.

## File Uploads (Event Logs)
* Validate MIME type and file extension strictly.
* Enforce maximum file size limits via Spring configuration.
* Prevent path traversal: Never use user-provided filenames directly for storage paths; generate internal UUIDs for stored files.

## AI Security
* Prompt injection defenses implemented in system prompts.
* Sanitization of retrieved RAG contexts to prevent data poisoning effects.
