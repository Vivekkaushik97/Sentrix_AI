# API Decision Records

## API-001 — Standardized Envelopes
* **Decision**: All responses wrapped in `{ "data": ... }`. Errors in `{ "timestamp", "status", "code"... }`.
* **Reason**: Consistent parsing on the React frontend.

## API-002 — Asynchronous Polling
* **Decision**: EVTX and Report generation will use 202 Accepted + Client Polling.
* **Reason**: Simpler to implement in Spring Boot than WebSockets for the initial phase.

## API-003 — No Public RAG API
* **Decision**: RAG operations are abstracted behind the Chat API.
* **Reason**: Exposing raw vector DB operations poses security risks and violates the intended UX.

## API-004 — History Route Mapping
* **Decision**: Frontend `/history` maps to `/api/v1/analyses`.
* **Reason**: Prevents redundant endpoint logic.

## TBD Decisions
* **TBD-001**: Exact structure of Fraud input features JSON.
* **TBD-002**: Chat Streaming (SSE vs blocking REST).
* **TBD-003**: Object storage mechanism for EVTX uploads.
