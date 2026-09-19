# Database Requirements Traceability

| Feature | Data Requirement | Entity | Relationship | Future API Route | Future Phase |
|---|---|---|---|---|---|
| Dashboard | Unified Risk view | `analysis_records` | `anonymous_sessions` -> `analysis_records` | `/api/v1/dashboard` | Phase 13 |
| UPI Fraud | Inputs & Probabilities | `fraud_analyses`, `fraud_transactions` | `analysis_records` -> `fraud_analyses` | `/api/v1/fraud` | Phase 5 |
| Event Logs | Parsed threats | `uploaded_files`, `log_analyses`, `threat_indicators` | `log_analyses` -> `threat_indicators` | `/api/v1/event-logs` | Phase 6 |
| CVE | Global & Local info | `cve_records`, `cve_analyses` | `cve_records` -> `cve_analyses` | `/api/v1/cves` | Phase 7 |
| AI Assistant | Chat transcripts | `chat_sessions`, `chat_messages` | `anonymous_sessions` -> `chat_sessions` | `/api/v1/chat` | Phase 8 |
| RAG | Embedded knowledge | `knowledge_documents`, `knowledge_chunks` | `documents` -> `chunks` | `/api/v1/rag` | Phase 9 |
| Reports | Generated PDFs | `security_reports` | `anonymous_sessions` -> `reports` | `/api/v1/reports` | Phase 14 |
| History | Past actions | `analysis_records` | (Filtered via Session) | `/api/v1/analyses` | Phase 15 |
| Anon Sessions | State linking | `anonymous_sessions` | (Root entity) | `/api/v1/session` | Phase 4 |
