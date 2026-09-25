# Phase 0: Redis Contract

This document explicitly defines the role of Redis within the Sentrix AI architecture.

## 1. Core Principle
**Redis is NOT the permanent source of truth.** It is an ephemeral, in-memory data store used strictly for caching, session management, and temporary state. Any data stored in Redis must be recoverable, recalculable, or non-critical if the Redis instance crashes or is flushed.

## 2. Approved Redis Responsibilities

### A. Anonymous Sessions (Spring Session)
*   **Usage**: Store active `SESSION_ID` mappings and session metadata.
*   **Why**: Fast validation on every HTTP request; native TTL support for automatic session expiration.
*   **Key Format**: Managed by Spring Session (e.g., `spring:session:sessions:<id>`).

### B. External API Caching (CVE Data)
*   **Usage**: Cache responses from external vulnerability databases (like NVD).
*   **Why**: External APIs are slow and enforce strict rate limits. Caching highly requested CVEs prevents API bans and improves UX.
*   **TTL**: E.g., 24 hours.
*   **Key Format**: `sentrix:cache:cve:<cve_id>`

### C. Temporary Processing State (Job Tracking)
*   **Usage**: Track the real-time status of asynchronous RabbitMQ jobs (e.g., EVTX parsing, PDF generation).
*   **Why**: Polling PostgreSQL constantly for job status updates is inefficient. Redis provides fast read/write for status flags (PENDING, PROCESSING, COMPLETED, FAILED).
*   **TTL**: E.g., 1 hour after job completion.
*   **Key Format**: `sentrix:jobs:status:<job_id>`

### D. Rate Limiting (If Required Later)
*   **Usage**: Track request counts per IP or Session ID to prevent abuse.
*   **Why**: Redis is the standard tool for sliding-window or token-bucket rate limit algorithms across multiple instances.

## 3. Explicitly Forbidden Uses

*   **Do NOT** use Redis to store permanent Chat History. (Use PostgreSQL).
*   **Do NOT** use Redis to store final Fraud Analysis results or Event Log reports. (Use PostgreSQL).
*   **Do NOT** use Redis to store vector embeddings. (Use pgvector in PostgreSQL).

## 4. Configuration Requirements
*   All custom keys must use a standard prefix: `sentrix:<domain>:<identifier>`.
*   Every key MUST have a Time-To-Live (TTL) set. Unbounded keys are forbidden.
