# Phase 9: Current State Audit

## Infrastructure
- **Java**: 25 LTS
- **Framework**: Spring Boot 3.5.4
- **Database**: PostgreSQL (via Supabase) with Flyway migrations.
- **Messaging**: RabbitMQ used for asynchronous event ingestion (`SecurityEvent` streams).
- **Cache**: Redis used primarily as a Spring Session store.
- **Frontend**: React + Vite + TypeScript, using Tailwind CSS and `shadcn/ui`.

## Database Migrations
1. `V1__init_schema.sql` - Core application shell (User context, basic analysis records).
2. `V2__windows_event_intelligence.sql` - Windows security models.
3. `V3__upi_security.sql` - Financial UPI transactions and rule evaluation history.
4. `V4__security_incidents.sql` - Security correlation engine tables (`security_incidents`, `incident_events`).

## Modularity & Existing Pipelines
- **RAG / AI**: Context-based AI Assistant implemented in `AIAssistant` module that explains records without hallucinating data.
- **Event Pipeline**: Highly abstracted `SecurityEventProcessor` listening to RabbitMQ to stream data to specific modules (UPI, Windows, Incidents, SSE).
- **Live Stream**: Backend exposes `SseEmitter` endpoints on `/api/v1/live/events` mapping backend queues to the React dashboard.

## Gaps for Phase 9
- There is currently no high-level **Investigation** model. Incidents exist, but an investigation allows a human analyst to bundle incidents, individual UPI/Windows events, reports, and AI notes into a chronological timeline case.
- Advanced querying (filtering millions of rows) is heavily reliant on basic JPA methods right now.
- Operations tracking, hardening, and observability (metrics exposing) need polish.

## Risks
- Expanding to an overarching Investigation abstraction risks duplicating logic from `SecurityIncident`. We must differentiate carefully: `SecurityIncident` is an automated machine grouping of alerts; `Investigation` is an active human-driven workspace encompassing multiple machine incidents and manual context.
