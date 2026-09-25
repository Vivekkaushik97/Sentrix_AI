# Phase 3: Event Log Analysis Module

This document details the Event Log Analysis module built during Step 5.

## Architecture
The event log module uses a deterministic rule-based engine to evaluate incoming security logs. It extends the `Analysis` root entity.

## Domain Model
- `EventLogAnalysis`: Extends `Analysis` to store log-specific data (`logSource`, `totalEvents`, `threatsDetected`, `rawLog`, `findings`).

## Rules
1. **FailedAuthRule**: Detects repeated authentication failures (>=3 attempts trigger High Severity, <3 trigger Low Severity).
2. **PrivilegeEscalationRule**: Flags any log where `eventType` is `PRIVILEGE_ESCALATION` or `action` contains "admin privilege".

## API Endpoints
- **POST `/api/v1/event-logs/analyze`**: Accepts a batch JSON list of `EventLogRequestDto` (limit 1000). Evaluates logs synchronously, persists the `EventLogAnalysis` entity, and returns an `EventLogAnalysisResponseDto`.
- **GET `/api/v1/event-logs/{id}`**: Retrieves a past event log analysis.

## Persistence
- Schema uses the `V1__init_schema.sql` `event_log_analyses` table using `JOINED` inheritance with `analyses`.
- No new migration was needed as the base V1 schema supported this implementation.

## Tests
- Unit tests exist for individual rules (`EventLogRulesTest`).
- Integration tests cover the API endpoints using `@WebMvcTest` with mocked services to ensure security is properly configured (`EventLogControllerIntegrationTest`).
