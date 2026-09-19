# API Design Principles

## Core Conventions
* **Base Path**: `/api/v1`
* **Protocol**: HTTPS (enforced in production).
* **Format**: `application/json` for all bodies except file uploads (`multipart/form-data`).

## Resource Naming
* Nouns, pluralized, lowercase, kebab-case (e.g., `/event-logs`, NOT `/eventLogs` or `/eventLog`).
* Nested resources used when naturally scoped (e.g., `/chat/sessions/{id}/messages`).

## HTTP Methods
* `GET`: Retrieve resource(s). Safe, idempotent.
* `POST`: Create resource. Not idempotent.
* `PUT`: Replace resource fully. Idempotent. (Rarely used in this API).
* `PATCH`: Update partial resource.
* `DELETE`: Remove resource.

## HTTP Status Codes
* `200 OK`: Success (GET, PATCH, DELETE).
* `201 Created`: Success (POST).
* `202 Accepted`: Async job accepted (POST).
* `400 Bad Request`: Validation failure.
* `401 Unauthorized`: Missing or invalid session cookie.
* `403 Forbidden`: Session does not own the resource.
* `404 Not Found`: Resource does not exist.
* `413 Payload Too Large`: EVTX upload exceeds limits.
* `429 Too Many Requests`: Rate limit exceeded.
* `500 Internal Server Error`: Unhandled backend exception.

## Data Types
* **IDs**: `UUIDv4` standard (String), except CVEs (String).
* **Timestamps**: ISO 8601 string format in UTC (e.g., `2024-10-01T12:00:00Z`).
* **Enums**: Uppercase strings (e.g., `"PENDING"`, `"CRITICAL"`).

## Patterns
* **Pagination**: Offset-based (page, size).
* **Correlation IDs**: `X-Request-ID` headers to trace logs.
