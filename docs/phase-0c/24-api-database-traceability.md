# API to Database Traceability

| API Group | Primary DB Entities Touched | Redis Usage | RabbitMQ Usage |
| --- | --- | --- | --- |
| `/session` | `anonymous_sessions` | Active TTL Storage | None |
| `/analyses` | `analysis_records` | Rate Limiting | None |
| `/fraud` | `fraud_analyses`, `fraud_transactions`, `analysis_records` | Rate Limiting | TBD (If ML is slow) |
| `/event-logs`| `uploaded_files`, `log_analyses`, `threat_indicators`, `analysis_records` | Rate Limiting | **YES** (Parser Worker) |
| `/cves` | `cve_records`, `cve_analyses`, `analysis_records` | Rate Limiting | None |
| `/chat` | `chat_sessions`, `chat_messages` | Rate Limiting | None |
| `/reports` | `security_reports` | Rate Limiting | **YES** (PDF Worker) |
| `/dashboard`| `analysis_records` (Projections) | Caching (TBD) | None |

No orphan API concepts exist. All paths touch DB entities defined in Phase 0B.
