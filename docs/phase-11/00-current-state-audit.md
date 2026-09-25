# Current State Audit (Phase 11)

## Architecture Overview
Sentrix AI currently implements a microservice-like layered architecture with a Spring Boot backend and React frontend.

## Reusable Components
- **RabbitMQ Architecture**: Event orchestration is handled via RabbitMQ (e.g. `sentrix.event.exchange`).
- **Redis Foundation**: Used for short-lived state, idempotency, and caching.
- **Server-Sent Events (SSE)**: For real-time updates.
- **AI Abstraction**: `AiProvider` and RAG for advisory output.
- **Security Context Model**: Highly detailed `SecurityContext` models representing nodes and edges (graphs) of security incidents and investigations.
- **Frontend Design System**: Premium SOC-style React components including DataGrid, Card, Action panels.

## Database Structure
PostgreSQL remains the single source of truth for authoritative entities (Investigations, Incidents, Contexts).
Currently, flyway migrations manage tables for phases 1-10.

## Existing Security Boundaries
- AI is strictly advisory.
- Existing exceptions handle deterministic states safely.
- No direct arbitrary shell/system interactions.

## Integration Points
- Windows Events
- UPI APIs
- CVE Intelligence
- RAG abstraction layers.
- Redis, PostgreSQL, and RabbitMQ.

## Risks
- Direct modification of execution code without proper domain boundary implementations could lead to autonomous execution.
- Allowing AI to generate raw actions could compromise the "Controlled Security Action" paradigm.

## Phase 11 Implementation Boundaries
- Must build `com.sentrix.ai.action`.
- Strictly Human-approved actions.
- Execute ONLY approved and allowlisted actions.
- Audit trail for all executed operations.
- Actions cannot interact dynamically via terminal or arbitrary systems; strictly typed actions operating on internal state.
