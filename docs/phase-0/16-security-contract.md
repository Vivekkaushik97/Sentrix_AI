# Phase 0: Security Architecture Contract

This document defines the security requirements and safeguards for Sentrix AI. Security must be built-in, not bolted on later.

## 1. Transport Security
*   **HTTPS/TLS**: All traffic between the frontend, backend, and external APIs must be encrypted using TLS 1.2 or higher. No plaintext HTTP is permitted in production.

## 2. Backend Security (Spring Security)
*   **Default Deny**: All endpoints must require authentication by default, with explicit exemptions configured for public endpoints (like the session init endpoint).
*   **Secure Headers**: Spring Security defaults must be enabled (X-Content-Type-Options: nosniff, X-Frame-Options: DENY, X-XSS-Protection).
*   **CORS (Cross-Origin Resource Sharing)**: Must be strictly configured to allow requests ONLY from the designated frontend domain. Wildcard `*` origins are forbidden in production.
*   **CSRF (Cross-Site Request Forgery)**: Given the use of cookie-based sessions, CSRF protection is highly recommended (or rely entirely on `SameSite=Strict` cookies, but Spring Security CSRF token pattern provides defense-in-depth).
*   **Rate Limiting**: Critical endpoints (e.g., File Upload, CVE Lookup, Chatbot API) must be rate-limited (e.g., using a Redis-based token bucket or Resilience4j) to prevent abuse and API cost exhaustion.

## 3. Database Security
*   **SQL Injection Prevention**: Hibernate/Spring Data JPA parameterizes all queries by default. Raw SQL (`@Query(nativeQuery = true)`) must be heavily scrutinized and strictly parameterized.
*   **Least Privilege**: The PostgreSQL user configured in `application.yml` should only have permissions necessary for the application (CRUD operations), not database schema modification (DDL) in production (schema managed by migration tools like Flyway/Liquibase).
*   **Secrets**: No API keys, passwords, or LLM tokens may be stored in plaintext in the database.

## 4. Session Security
*   **Cookies**: Session IDs must be stored in cookies with `HttpOnly`, `Secure`, and `SameSite` flags enabled.
*   **Data Isolation**: Every database query retrieving analysis or chat history must validate that the `session_id` column matches the current user's session.

## 5. File Upload Security (EVTX)
File uploads are a major attack vector.
*   **Extension Validation**: Reject anything that doesn't end in `.evtx` or `.xml`.
*   **MIME Type Validation**: Verify the actual content type, not just the extension.
*   **Size Limits**: Enforce a strict max file size limit (e.g., 50MB) in Spring Boot configuration (`spring.servlet.multipart.max-file-size`).
*   **Path Traversal Prevention**: Never use the original filename provided by the client when saving to disk. Generate a random UUID as the storage filename.
*   **Automatic Deletion**: Ensure temporary files are deleted immediately after processing or upon processing failure.

## 6. AI & RAG Security
*   **Prompt Injection**: LLMs can be manipulated by malicious inputs. Ensure system prompts are robust and establish clear boundaries.
*   **Data Leakage**: Do not send sensitive PII to external LLM providers.
*   **RAG Poisoning**: The knowledge base must only be populated from trusted, verified sources. Do not allow anonymous users to upload documents to the RAG vector database.

## 7. Logging & Error Handling
*   **No Information Leakage**: Stack traces must never be returned to the client in API responses (see Error Handling Strategy).
*   **Sanitized Logging**: Do not log session IDs, user inputs (which may contain PII), or API keys.
*   **Security Events**: Log significant security events (e.g., repeated rate limit violations, malformed file uploads) for monitoring.
