# Phase 0: Requirements Traceability Matrix

This matrix maps major project requirements to their planned implementation details, ensuring complete coverage of the Capstone project scope.

| Requirement | Module | Database Entities | API Group | Frontend Route | Future Phase | Verification |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Anonymous Usage** | `sentrix-session` | `anonymous_sessions` (Redis/PG) | `/session/*` | `/` (Implicit) | Phase 4 | User can use app without logging in. |
| **Interactive Dashboard** | `sentrix-dashboard` | N/A (Aggregates others) | `/dashboard/*` | `/dashboard` | Phase 13 | UI displays dynamic metrics. |
| **UPI Fraud Detection** | `sentrix-fraud` | `fraud_analyses`, `fraud_transactions` | `/fraud/*` | `/fraud/*` | Phase 5 | User submits transaction, receives score & explanation. |
| **EVTX Analysis** | `sentrix-eventlog` | `log_analyses`, `uploaded_files`, `threat_indicators` | `/event-logs/*` | `/event-logs/*` | Phase 6 & 12 | User uploads EVTX, receives threat report. |
| **CVE Intelligence** | `sentrix-cve` | `cve_records`, `cve_analyses` | `/cves/*` | `/cves/*` | Phase 7 | User searches CVE, receives details & explanation. |
| **Cybersecurity Chatbot** | `sentrix-ai` | `chat_sessions`, `chat_messages` | `/chat/*` | `/assistant` | Phase 8 | User chats with AI about security concepts. |
| **RAG Knowledge Base** | `sentrix-rag` | `knowledge_documents`, `knowledge_chunks`, `embeddings` | Internal API | N/A (Supports Chatbot) | Phase 9 | AI responses cite injected knowledge docs. |
| **Unified Risk Scoring** | `sentrix-common` | `analysis_records` | N/A (Nested in domain APIs) | N/A (UI Component) | Phase 5,6,7 | Dashboard shows normalized Low/Med/High risks. |
| **Analysis History** | `sentrix-analysis` | `analysis_records` | `/analyses` | `/history` | Phase 15 | User sees past scans in a paginated table. |
| **Security Reports** | `sentrix-report` | `security_reports` | `/reports/*` | `/reports` | Phase 14 | User downloads generated PDF report. |
| **Background Processing** | `sentrix-notification` | N/A (Redis Job Tracking) | `/jobs/*` (Implicit via polling) | UI Notifications | Phase 12 | Large EVTX parsing doesn't block UI. |
| **Health Monitoring** | `sentrix-monitoring` | N/A | `/actuator/*` | N/A | Phase 20 | Actuator exposes metrics to Prometheus. |
