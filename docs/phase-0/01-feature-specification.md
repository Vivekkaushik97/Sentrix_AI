# Phase 0: Feature Specification

This document defines all major features of Sentrix AI. If a requirement is unclear or needs external data validation before finalization, it is marked as **TO BE DECIDED**.

## A. Dashboard
* **Purpose**: Provide a central, high-level overview of the system's security posture and recent activities.
* **User Goal**: Quickly understand current risk levels, recent analyses, and system health at a glance.
* **Inputs**: Session context, request for dashboard metrics.
* **Outputs**: Aggregated metrics, recent analysis summaries, active risk scores.
* **Main Processing Steps**: Fetch recent records from `analysis`, `fraud`, `cve`, and `event_log` modules; aggregate and format for UI presentation.
* **Required Database Information**: `analysis_records`, `fraud_analyses`, `log_analyses`, `cve_analyses`.
* **External Dependencies**: None.
* **Security Considerations**: Data isolated by anonymous session ID.
* **AI/RAG Involvement**: None.
* **Synchronous/Asynchronous**: Synchronous.
* **Future Dependencies**: Relies on data populated by all other modules.

## B. Anonymous Session
* **Purpose**: Track user activity and isolate data without requiring user registration or login.
* **User Goal**: Use the platform seamlessly without providing PII (email/password).
* **Inputs**: Initial HTTP request.
* **Outputs**: HTTP-only session cookie containing a secure session identifier.
* **Main Processing Steps**: Generate UUID, store session metadata in Redis, set secure cookie.
* **Required Database Information**: `anonymous_sessions` (Redis or PostgreSQL).
* **External Dependencies**: None.
* **Security Considerations**: Secure HTTP-only cookies, SameSite=Strict/Lax, automatic expiration, session fixation protection.
* **AI/RAG Involvement**: None.
* **Synchronous/Asynchronous**: Synchronous.
* **Future Dependencies**: Foundational for all user-facing requests.

## C. UPI Fraud Detection
* **Purpose**: Analyze UPI transaction patterns to detect potentially fraudulent activity.
* **User Goal**: Submit transaction details and receive a probability score of fraud along with an explanation.
* **Inputs**: UPI transaction metadata (amount, time, velocity, merchant, etc. - exact fields **TO BE FINALIZED**).
* **Outputs**: Fraud probability, risk classification (e.g., HIGH, LOW), AI-generated explanation.
* **Main Processing Steps**: Validate input -> Extract features -> Run Java-based ML inference -> Generate AI explanation -> Store results.
* **Required Database Information**: `fraud_transactions`, `fraud_analyses`.
* **External Dependencies**: Java-based ML model (Tribuo/DJL).
* **Security Considerations**: Input validation, rate limiting.
* **AI/RAG Involvement**: Spring AI used to generate human-readable explanations of the model's output.
* **Synchronous/Asynchronous**: Synchronous (unless bulk upload is required - **TO BE DECIDED**).
* **Future Dependencies**: ML model training and selection.

## D. Windows Event Log Analysis
* **Purpose**: Parse and analyze Windows Event Logs (.evtx) to identify security threats, lateral movement, or malicious patterns.
* **User Goal**: Upload an EVTX file and view a parsed, analyzed threat report.
* **Inputs**: .evtx file upload.
* **Outputs**: Parsed events, identified threats, risk score, mitigation recommendations.
* **Main Processing Steps**: Accept file upload -> Validate -> Parse EVTX (Java library **TO BE DECIDED**) -> Analyze rules -> Generate report -> Store.
* **Required Database Information**: `uploaded_files`, `log_analyses`, `log_events`, `threat_indicators`.
* **External Dependencies**: Java EVTX parser library.
* **Security Considerations**: Strict file upload validation (size, MIME type, extension), path traversal prevention, temporary storage cleanup.
* **AI/RAG Involvement**: AI used to summarize complex event sequences or explain specific threat indicators.
* **Synchronous/Asynchronous**: Asynchronous (file parsing can be slow, requires RabbitMQ).
* **Future Dependencies**: RabbitMQ, File Storage Strategy.

## E. CVE Intelligence
* **Purpose**: Retrieve, store, and explain Common Vulnerabilities and Exposures (CVEs).
* **User Goal**: Search for a CVE and understand its impact, exploitability, and mitigation strategies.
* **Inputs**: CVE ID (e.g., CVE-2023-XXXX).
* **Outputs**: CVE details (CVSS, description, affected products), AI-generated summary/explanation.
* **Main Processing Steps**: Query local DB -> If not found, fetch from External API -> Store in DB -> Generate AI explanation -> Return to user.
* **Required Database Information**: `cve_records`, `cve_analyses`.
* **External Dependencies**: External CVE API (NVD, OpenCVE, etc. - **TO BE DECIDED**).
* **Security Considerations**: Rate limiting outbound API calls.
* **AI/RAG Involvement**: Spring AI used to translate technical CVE jargon into actionable intelligence.
* **Synchronous/Asynchronous**: Synchronous.
* **Future Dependencies**: External API key management.

## F. AI Cybersecurity Assistant
* **Purpose**: Provide a conversational interface for users to ask cybersecurity questions.
* **User Goal**: Interact with an AI assistant to clarify security concepts, ask for advice, or drill down into analysis results.
* **Inputs**: User chat messages.
* **Outputs**: AI responses.
* **Main Processing Steps**: Receive message -> Retrieve chat history -> Construct prompt -> Call LLM via Spring AI -> Stream/Return response -> Store history.
* **Required Database Information**: `chat_sessions`, `chat_messages`.
* **External Dependencies**: Configurable LLM Provider (via Spring AI).
* **Security Considerations**: Prompt injection defenses, filtering sensitive PII, bounding context.
* **AI/RAG Involvement**: Core AI feature.
* **Synchronous/Asynchronous**: Synchronous (potentially streaming).
* **Future Dependencies**: LLM selection, Prompt Engineering.

## G. RAG Knowledge Base
* **Purpose**: Augment the AI Assistant with a curated database of cybersecurity knowledge to reduce hallucinations and provide accurate, domain-specific answers.
* **User Goal**: Receive highly accurate AI responses backed by verified cybersecurity documentation.
* **Inputs**: User queries (via AI Assistant).
* **Outputs**: Context-augmented AI responses, source citations.
* **Main Processing Steps**: User query -> Generate embedding -> Similarity search in pgvector -> Assemble context -> LLM prompt -> Response.
* **Required Database Information**: `knowledge_documents`, `knowledge_chunks`, `knowledge_embeddings` (pgvector).
* **External Dependencies**: Embedding Model Provider, Configurable LLM.
* **Security Considerations**: RAG poisoning (validating knowledge ingestion), prompt boundary enforcement.
* **AI/RAG Involvement**: Core RAG feature.
* **Synchronous/Asynchronous**: Ingestion is Asynchronous; Retrieval is Synchronous.
* **Future Dependencies**: Cybersecurity corpus collection.

## H. Security Risk Scoring
* **Purpose**: Provide a standardized risk representation across all platform modules.
* **User Goal**: Compare the severity of different types of threats (Fraud, EVTX, CVE) using a common scale.
* **Inputs**: Raw scores/classifications from individual modules.
* **Outputs**: Normalized risk structure (RiskScore, Severity Enum: LOW, MEDIUM, HIGH, CRITICAL, Confidence).
* **Main Processing Steps**: Module-specific scoring algorithm -> Normalization layer.
* **Required Database Information**: Shared `analysis_records` metadata.
* **External Dependencies**: None.
* **Security Considerations**: None.
* **AI/RAG Involvement**: None.
* **Synchronous/Asynchronous**: Synchronous.
* **Future Dependencies**: Module implementations.

## I. Analysis History
* **Purpose**: Allow users to review their past submissions and analyses.
* **User Goal**: View a paginated list of previous fraud checks, EVTX uploads, and CVE lookups.
* **Inputs**: Session ID, Pagination parameters.
* **Outputs**: Paginated list of analysis metadata.
* **Main Processing Steps**: Query DB filtering by Session ID -> Format -> Return.
* **Required Database Information**: `analysis_records` (or module specific tables joined).
* **External Dependencies**: None.
* **Security Considerations**: Ensure users can only see their own session's history.
* **AI/RAG Involvement**: None.
* **Synchronous/Asynchronous**: Synchronous.
* **Future Dependencies**: Implementation of all analysis modules.

## J. Security Reports
* **Purpose**: Generate exportable summaries of security analyses.
* **User Goal**: Download a PDF or JSON report for a specific analysis (e.g., an EVTX threat report) to share with a team.
* **Inputs**: Analysis ID, Desired Format (PDF/JSON).
* **Outputs**: Downloadable file.
* **Main Processing Steps**: Fetch analysis data -> Format into report template -> Generate File -> Return.
* **Required Database Information**: `security_reports`, source analysis tables.
* **External Dependencies**: PDF Generation Library (e.g., OpenPDF or similar Java library - **TO BE DECIDED**).
* **Security Considerations**: Authorization (does the session own the analysis?).
* **AI/RAG Involvement**: AI used to generate executive summaries for the report.
* **Synchronous/Asynchronous**: Asynchronous (PDF generation can be heavy).
* **Future Dependencies**: RabbitMQ.

## K. Notifications (Status Tracking)
* **Purpose**: Inform the user when long-running background tasks complete.
* **User Goal**: Know when an uploaded EVTX file is fully parsed and analyzed.
* **Inputs**: Job ID.
* **Outputs**: Job Status (PENDING, PROCESSING, COMPLETED, FAILED).
* **Main Processing Steps**: Client polls status endpoint or connects via SSE/WebSocket (**TO BE DECIDED**).
* **Required Database Information**: `analysis_records` status fields.
* **External Dependencies**: None (Redis potentially for fast status lookup).
* **Security Considerations**: Session validation.
* **AI/RAG Involvement**: None.
* **Synchronous/Asynchronous**: Synchronous (status read).
* **Future Dependencies**: RabbitMQ integration.

## L. System Health
* **Purpose**: Provide operational visibility into backend services.
* **User Goal**: Ensure the platform is running smoothly (primarily for admins/developers, but generic status for users).
* **Inputs**: None.
* **Outputs**: Health metrics (DB connection, Redis connection, RabbitMQ connection).
* **Main Processing Steps**: Spring Boot Actuator exposes health endpoints.
* **Required Database Information**: None.
* **External Dependencies**: None.
* **Security Considerations**: Do not expose sensitive internal IP addresses or stack traces.
* **AI/RAG Involvement**: None.
* **Synchronous/Asynchronous**: Synchronous.
* **Future Dependencies**: Actuator configuration.
