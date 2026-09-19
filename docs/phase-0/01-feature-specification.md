# Feature Specification

### A. Dashboard
* **Purpose**: Provide a centralized, high-level overview of system security posture and recent activities.
* **User goal**: Quickly assess system status, latest threats, active sessions, and recent analyses.
* **Inputs**: User session token.
* **Outputs**: Aggregated metrics, charts, and recent alerts.
* **Main processing steps**: Query database for recent analyses and summarize results.
* **Required database information**: Analysis histories, risk scores, session data.
* **External dependencies**: None.
* **Security considerations**: Only show data relevant to the current anonymous session.
* **AI/RAG involvement**: AI may generate a natural language summary of the dashboard state.
* **Synchronous/asynchronous behavior**: Synchronous data retrieval.
* **Future dependencies**: Real-time notifications (TO BE DECIDED).

### B. Anonymous Session
* **Purpose**: Track user activity without requiring PII or email/password authentication.
* **User goal**: Seamlessly interact with the platform securely.
* **Inputs**: Initial HTTP request.
* **Outputs**: Session ID, HTTP-only cookie.
* **Main processing steps**: Generate unique session ID, store in Redis, attach to response.
* **Required database information**: `anonymous_sessions` (PostgreSQL), session cache (Redis).
* **External dependencies**: None.
* **Security considerations**: Secure HTTP-only cookies, SameSite protection, automatic expiration.
* **AI/RAG involvement**: None.
* **Synchronous/asynchronous behavior**: Synchronous.
* **Future dependencies**: Session limits/rate limiting.

### C. UPI Fraud Detection
* **Purpose**: Identify potentially fraudulent UPI transactions.
* **User goal**: Check if a specific transaction or pattern is fraudulent.
* **Inputs**: Transaction details (amount, time, entities, velocity).
* **Outputs**: Fraud probability, risk classification, AI explanation.
* **Main processing steps**: Validate input, extract features, run Java-based ML inference, generate explanation.
* **Required database information**: `fraud_analyses`, `fraud_transactions`.
* **External dependencies**: Java-compatible ML inference engine (TO BE FINALIZED).
* **Security considerations**: Do not store sensitive banking credentials.
* **AI/RAG involvement**: Generates explanation for the fraud classification.
* **Synchronous/asynchronous behavior**: Synchronous.
* **Future dependencies**: Batch analysis via RabbitMQ.

### D. Windows Event Log Analysis
* **Purpose**: Parse and analyze Windows EVTX files or exported XML logs for security threats.
* **User goal**: Upload event logs and receive a threat analysis report.
* **Inputs**: Event log file (EVTX/XML).
* **Outputs**: Parsed events, threat indicators, overall risk score.
* **Main processing steps**: Upload file, parse events, match against threat signatures/patterns, score risk.
* **Required database information**: `uploaded_files`, `log_analyses`, `log_events`, `threat_indicators`.
* **External dependencies**: Java EVTX parser (TO BE FINALIZED).
* **Security considerations**: Safe file handling, path traversal protection, size limits.
* **AI/RAG involvement**: AI summarizes the threat findings.
* **Synchronous/asynchronous behavior**: Asynchronous (RabbitMQ) for large files, synchronous for small.
* **Future dependencies**: Integration with CVE database for cross-referencing.

### E. CVE Intelligence
* **Purpose**: Fetch and analyze Common Vulnerabilities and Exposures.
* **User goal**: Lookup CVE details and understand their impact.
* **Inputs**: CVE ID or search terms.
* **Outputs**: CVE details, CVSS score, AI explanation.
* **Main processing steps**: Query local DB or external API, retrieve data, generate AI explanation.
* **Required database information**: `cve_records`, `cve_analyses`.
* **External dependencies**: External CVE data source/API (TO BE FINALIZED).
* **Security considerations**: Rate limiting on external APIs.
* **AI/RAG involvement**: AI explains the vulnerability in simple terms.
* **Synchronous/asynchronous behavior**: Synchronous.
* **Future dependencies**: Background synchronization of CVE database.

### F. AI Cybersecurity Assistant
* **Purpose**: Provide an interactive conversational interface for cybersecurity queries.
* **User goal**: Ask questions about platform findings or general cybersecurity topics.
* **Inputs**: User prompt/message.
* **Outputs**: AI response.
* **Main processing steps**: Receive prompt, assemble context, query LLM via Spring AI, return response.
* **Required database information**: `chat_sessions`, `chat_messages`.
* **External dependencies**: Configurable LLM provider.
* **Security considerations**: Prompt injection defenses, output validation, sensitive data handling.
* **AI/RAG involvement**: Core feature.
* **Synchronous/asynchronous behavior**: Synchronous (or streaming).
* **Future dependencies**: RAG integration.

### G. RAG Knowledge Base
* **Purpose**: Ground AI responses in a specific cybersecurity knowledge base.
* **User goal**: Receive accurate, domain-specific answers.
* **Inputs**: Knowledge documents (ingestion), User queries (retrieval).
* **Outputs**: Contextualized AI responses.
* **Main processing steps**: Document -> Chunking -> Embedding -> pgvector -> Similarity Search -> LLM Context.
* **Required database information**: `knowledge_documents`, `knowledge_chunks`, `knowledge_embeddings` (pgvector).
* **External dependencies**: Embedding provider, LLM provider.
* **Security considerations**: Validate ingested documents, prevent poisoning.
* **AI/RAG involvement**: Core feature.
* **Synchronous/asynchronous behavior**: Asynchronous ingestion (RabbitMQ), Synchronous retrieval.
* **Future dependencies**: None.

### H. Security Risk Scoring
* **Purpose**: Provide a normalized risk metric across all analysis types.
* **User goal**: Quickly understand the severity of any finding.
* **Inputs**: Raw analysis outputs (Fraud, Event Logs, CVEs).
* **Outputs**: Normalized risk score (LOW, MEDIUM, HIGH, CRITICAL).
* **Main processing steps**: Map module-specific scores to the common framework.
* **Required database information**: `analysis_records`.
* **External dependencies**: None.
* **Security considerations**: None.
* **AI/RAG involvement**: AI can explain the reasoning behind a score.
* **Synchronous/asynchronous behavior**: Synchronous.
* **Future dependencies**: None.

### I. Analysis History
* **Purpose**: Allow users to review their past actions and analyses.
* **User goal**: Retrieve a previous report or analysis result.
* **Inputs**: Session ID, pagination parameters.
* **Outputs**: List of past analyses.
* **Main processing steps**: Query database filtering by session ID.
* **Required database information**: Analysis tables.
* **External dependencies**: None.
* **Security considerations**: Strict data isolation per session.
* **AI/RAG involvement**: None.
* **Synchronous/asynchronous behavior**: Synchronous.
* **Future dependencies**: None.

### J. Security Reports
* **Purpose**: Generate downloadable or shareable summaries of analyses.
* **User goal**: Export findings for external use.
* **Inputs**: Analysis IDs.
* **Outputs**: Formatted report (PDF/HTML - TO BE DECIDED).
* **Main processing steps**: Gather analysis data, format into report, store metadata.
* **Required database information**: `security_reports`.
* **External dependencies**: PDF generation library (TO BE FINALIZED).
* **Security considerations**: Ensure user can only export their own data.
* **AI/RAG involvement**: AI drafts the executive summary of the report.
* **Synchronous/asynchronous behavior**: Asynchronous generation via RabbitMQ.
* **Future dependencies**: None.

### K. Notifications / Status Tracking
* **Purpose**: Inform users of long-running job completion.
* **User goal**: Know when Event Log analysis or Report generation is done.
* **Inputs**: Job ID.
* **Outputs**: Job status.
* **Main processing steps**: Poll or push status updates.
* **Required database information**: Job status in Redis or PostgreSQL.
* **External dependencies**: None.
* **Security considerations**: None.
* **AI/RAG involvement**: None.
* **Synchronous/asynchronous behavior**: Asynchronous.
* **Future dependencies**: Websocket integration.

### L. System Health/Status
* **Purpose**: Monitor platform availability.
* **User goal**: Ensure the system is operational.
* **Inputs**: None.
* **Outputs**: Health status JSON.
* **Main processing steps**: Spring Boot Actuator checks DB, Redis, RabbitMQ.
* **Required database information**: None.
* **External dependencies**: None.
* **Security considerations**: Do not expose sensitive internal metrics publicly.
* **AI/RAG involvement**: None.
* **Synchronous/asynchronous behavior**: Synchronous.
* **Future dependencies**: Prometheus/Grafana.
