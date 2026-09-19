# Requirements Traceability Matrix

| Requirement | Module | Database Entities | API Group | Frontend Route | Future Phase | Verification |
|---|---|---|---|---|---|---|
| Dashboard Overview | `dashboard` | `analysis_records` | `/api/v1/dashboard` | `/dashboard` | Phase 13 | UI correctly renders stats |
| Anonymous Usage | `session` | `anonymous_sessions` | `/api/v1/session` | (Global) | Phase 4 | App functions without login |
| Fraud Detection | `fraud` | `fraud_analyses`, `fraud_transactions` | `/api/v1/fraud` | `/fraud` | Phase 5 | Accurate ML classification |
| Event Log Parsing | `eventlog` | `uploaded_files`, `log_analyses` | `/api/v1/event-logs` | `/event-logs` | Phase 6 & 12 | File parses successfully |
| CVE Lookup | `cve` | `cve_records`, `cve_analyses` | `/api/v1/cves` | `/cves` | Phase 7 | API fetches correct CVSS |
| AI Chat | `ai` | `chat_sessions`, `chat_messages` | `/api/v1/chat` | `/assistant` | Phase 8 | LLM responds contextually |
| RAG Knowledge | `rag` | `knowledge_documents`, `knowledge_chunks` | `/api/v1/rag` | (Background) | Phase 9 | Answers grounded in docs |
| History View | `analysis` | `analysis_records` | `/api/v1/analyses` | `/history` | Phase 15 | Past analyses visible |
| PDF Reports | `report` | `security_reports` | `/api/v1/reports` | `/reports` | Phase 14 | PDF download succeeds |
| Async Processing | `notification`| Job status fields | (Integrated) | (Loaders) | Phase 12 | Main thread not blocked |
