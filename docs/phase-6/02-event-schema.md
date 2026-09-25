# Phase 6: Event Schema

## Database Tables
Established via Flyway Migration `V2__windows_event_intelligence.sql`.

### `windows_events`
The canonical store for raw and normalized Windows Event Log records.
- `id`: UUID (Primary Key)
- `timestamp`: OffsetDateTime
- `computer_name`: String (e.g. "DESKTOP-EXAMPLE")
- `log_name`: String (e.g. "Security", "System")
- `provider_name`: String (e.g. "Microsoft-Windows-Security-Auditing")
- `event_id`: Integer
- `level`: String
- `task`: String
- `opcode`: String
- `keywords`: String
- `system_user`: String
- `process_id`: Long
- `thread_id`: Long
- `channel`: String
- `message`: Text (Escaped for XSS on frontend)
- `raw_event`: Text (Original JSON/XML)
- `source`: String (The collector that sent it)
- `ingestion_timestamp`: OffsetDateTime

### `windows_event_analyses`
Extends the generic `analyses` table. Links the set of detections and correlations for a specific evaluation run.
- `id`: UUID (FK to `analyses.id`)
- `risk_score`: Integer (0-100)
- `computer_name`: String

### `windows_event_detections`
Specific security findings triggered by rules.
- `id`: UUID
- `windows_event_id`: UUID
- `analysis_id`: UUID
- `rule_id`: String (e.g. "WIN-FAILED-AUTH")
- `severity`: String (LOW, MEDIUM, HIGH, CRITICAL)
- `reason`: Text
- `evidence`: Text

### `windows_event_correlations`
Sequences of events linked together.
- `id`: UUID
- `analysis_id`: UUID
- `correlation_key`: String (e.g. "USER:SYSTEM,TIME:10m")
- `explanation`: Text
- `severity`: String
- `events`: Many-to-Many mapping to `windows_events`.
