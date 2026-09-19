# Event Log Database Model

## Persistence Strategy
Windows Event Logs (`.evtx`) can contain millions of events, totaling hundreds of megabytes.

**CRITICAL DECISION**: The database **will not** store every raw event. 

1. **Raw Event Storage**: Discarded from PostgreSQL. The raw `.evtx` file is kept temporarily in object storage (tracked via `uploaded_files`).
2. **Analysis Metadata**: `log_analyses` stores aggregate counts (`total_events`, `suspicious_events`).
3. **Threat Indicators**: Only events flagged as suspicious/malicious by the Java parser will be persisted as individual rows in `threat_indicators`.

### threat_indicators
Stores only actionable intelligence:
* `event_id`: e.g., '4625'.
* `severity`: Extracted from the event.
* `description`: The localized string or relevant event data explaining why it was flagged.

This strategy ensures PostgreSQL is not bloated with gigabytes of benign informational OS events, keeping the database highly performant and scalable.
