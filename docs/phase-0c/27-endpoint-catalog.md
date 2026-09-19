# Endpoint Catalog

*This summarizes the defined API contract.*

| Method | Path | Purpose | Sync/Async |
|---|---|---|---|
| POST | `/api/v1/session` | Create Session | Sync |
| GET | `/api/v1/session/me` | Check Session | Sync |
| DELETE | `/api/v1/session` | Terminate Session | Sync |
| POST | `/api/v1/fraud` | Analyze Fraud | Sync |
| GET | `/api/v1/fraud/{id}` | View Fraud Result | Sync |
| POST | `/api/v1/event-logs/upload` | Parse Logs | **Async** |
| GET | `/api/v1/event-logs/{id}` | Poll Log Status | Sync |
| GET | `/api/v1/cves/search?q=` | Search NVD | Sync |
| POST | `/api/v1/cves/{id}/analyze` | AI CVE context | Sync |
| GET | `/api/v1/analyses` | List History | Sync |
| POST | `/api/v1/chat/sessions` | Create Chat | Sync |
| POST | `/api/v1/chat/sessions/{id}/messages`| Send Message | Sync |
| GET | `/api/v1/dashboard/summary` | Load Dashboard | Sync |
| POST | `/api/v1/reports` | Generate PDF | **Async** |
| GET | `/api/v1/reports/{id}/download` | Fetch PDF | Sync |
