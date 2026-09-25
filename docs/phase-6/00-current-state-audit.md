# Phase 6: Current State Audit

## Objective
Audit the existing architecture (Phases 1-5) for any existing Windows Event Log capabilities before implementing Phase 6.

## Findings

1. **Existing Event Log Implementation (`com.sentrix.ai.eventlog.*`)**:
   - The system already has an `EventLogAnalysisService`, `EventLogController`, and `EventLogRequestDto`.
   - These exist under the `/api/v1/event-logs` endpoint.
   - The schema for these events is generic: `timestamp`, `eventType`, `source`, `sourceIp`, `username`, `action`, `status`, `resource`, `message`.
   - **Conclusion**: This is a generic event log analyzer from a prior phase, **not** specific to Windows Event Logs. It does not contain fields like `EventID`, `LogName`, `ProviderName`, etc.

2. **Database Migrations**:
   - `V1__init_schema.sql` establishes tables for generic `analyses` (type, status, severity, timestamps).
   - `EventLogAnalysis` maps to the `analyses` table via inheritance.
   - **Conclusion**: There is no table dedicated to storing Windows Event Log structures or detections. A new migration (`V2__windows_event_intelligence.sql`) is required.

3. **Frontend**:
   - There is an existing "Event Logs" page (`EventLogs.tsx`) which displays generic event logs.
   - **Conclusion**: A new UI or heavy adaptation of the existing UI will be needed to handle Windows Event Log specifics (Event ID, Provider, LogName).

4. **AI / RAG Foundation**:
   - `SecurityContextBuilder` and `VectorStore` abstractions exist.
   - Currently, `SecurityContextBuilder` fetches the top 5 recent `Analysis` records.
   - **Conclusion**: Phase 6 will need to extend `SecurityContextBuilder` or provide a `WindowsEvent` specific context provider.

## Next Steps
- Do not modify or delete the existing `/api/v1/event-logs` generic functionality.
- Build a new isolated `com.sentrix.ai.windowsevent.*` module.
- Create `V2__windows_event_intelligence.sql`.
- Implement `WindowsEvent` entity, ingestion API, normalizer, rule engine, and correlation engine.
