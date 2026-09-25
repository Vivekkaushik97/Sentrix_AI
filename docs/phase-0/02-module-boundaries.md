# Phase 0: Module Boundaries

The Sentrix AI backend is designed with a modular, maintainable architecture. The system avoids a monolithic package structure by separating domain concerns. Modules are expected to communicate through defined service interfaces or asynchronous events (RabbitMQ), ensuring they can evolve independently.

## 1. sentrix-config
* **Responsibility**: Global application configuration, environment variable mapping, bean definitions.
* **Entities Owned**: None.
* **Services Owned**: None.
* **Controllers Owned**: None.
* **Repositories Owned**: None.
* **Dependencies**: Relied upon by all modules.
* **Data it may access**: Environment variables, application.yml.
* **Data it must not directly manipulate**: Database entities.

## 2. sentrix-common
* **Responsibility**: Shared utilities, base exception classes, global constants, DTO templates (e.g., standard API Response), and the risk scoring normalization logic.
* **Entities Owned**: Base entity classes (e.g., `BaseEntity` with UUID, created_at, updated_at).
* **Services Owned**: Utility services (Date formatting, String manipulation).
* **Controllers Owned**: Global Exception Handler (`@RestControllerAdvice`).
* **Repositories Owned**: None.
* **Dependencies**: Relied upon by all modules.
* **Data it may access**: Generic data passing through utils.
* **Data it must not directly manipulate**: Domain-specific database tables.

## 3. sentrix-security
* **Responsibility**: Security filter chains, CORS, CSRF, rate limiting configuration, generic input validation concepts.
* **Entities Owned**: None.
* **Services Owned**: Rate limiting service, Security context resolution.
* **Controllers Owned**: None.
* **Repositories Owned**: None.
* **Dependencies**: `sentrix-config`, `sentrix-common`, `sentrix-session`.
* **Data it may access**: HTTP Request Headers, Cookies.
* **Data it must not directly manipulate**: Domain entities.

## 4. sentrix-session
* **Responsibility**: Managing anonymous user sessions, cookie generation, session validation.
* **Entities Owned**: `AnonymousSession`.
* **Services Owned**: `SessionManagementService`.
* **Controllers Owned**: `SessionController`.
* **Repositories Owned**: `SessionRepository` (Redis/PostgreSQL).
* **Dependencies**: `sentrix-common`, `sentrix-config`.
* **Data it may access**: Redis session cache.
* **Data it must not directly manipulate**: Analysis records.

## 5. sentrix-fraud
* **Responsibility**: Handling UPI transaction validation, ML inference, and fraud result generation.
* **Entities Owned**: `FraudAnalysis`, `FraudTransaction`.
* **Services Owned**: `FraudDetectionService`, `MLInferenceService` (Java).
* **Controllers Owned**: `FraudController`.
* **Repositories Owned**: `FraudAnalysisRepository`, `FraudTransactionRepository`.
* **Dependencies**: `sentrix-common`, `sentrix-ai`, `sentrix-session`, `sentrix-analysis`.
* **Data it may access**: Fraud tables, ML model files.
* **Data it must not directly manipulate**: Event Logs, CVE records.

## 6. sentrix-eventlog
* **Responsibility**: Parsing uploaded Windows Event Logs (.evtx), extracting threats, evaluating rules.
* **Entities Owned**: `LogAnalysis`, `LogEvent`, `ThreatIndicator`.
* **Services Owned**: `EvtxParserService`, `ThreatDetectionService`.
* **Controllers Owned**: `EventLogController`.
* **Repositories Owned**: `LogAnalysisRepository`, `LogEventRepository`, `ThreatIndicatorRepository`.
* **Dependencies**: `sentrix-storage`, `sentrix-ai`, `sentrix-common`, `sentrix-analysis`, `sentrix-session`.
* **Data it may access**: Log tables, Object storage (files).
* **Data it must not directly manipulate**: Fraud records.

## 7. sentrix-cve
* **Responsibility**: Interfacing with external CVE APIs, retrieving vulnerability data, caching/persisting records.
* **Entities Owned**: `CveRecord`, `CveAnalysis`.
* **Services Owned**: `CveLookupService`, `ExternalCveApiService`.
* **Controllers Owned**: `CveController`.
* **Repositories Owned**: `CveRecordRepository`, `CveAnalysisRepository`.
* **Dependencies**: `sentrix-common`, `sentrix-ai`, `sentrix-analysis`, `sentrix-session`.
* **Data it may access**: CVE tables, external API networks.
* **Data it must not directly manipulate**: User chat logs.

## 8. sentrix-ai
* **Responsibility**: Orchestrating LLM interactions via Spring AI. Constructing prompts for explaining fraud, CVEs, or event logs.
* **Entities Owned**: `ChatSession`, `ChatMessage`.
* **Services Owned**: `AiExplanationService`, `ChatAssistantService`.
* **Controllers Owned**: `ChatController`.
* **Repositories Owned**: `ChatSessionRepository`, `ChatMessageRepository`.
* **Dependencies**: `sentrix-common`, `sentrix-rag`, `sentrix-session`.
* **Data it may access**: Chat tables, LLM API network.
* **Data it must not directly manipulate**: Raw transaction data (must be passed via DTOs).

## 9. sentrix-rag
* **Responsibility**: Ingesting cybersecurity knowledge, embedding text, performing similarity search in pgvector.
* **Entities Owned**: `KnowledgeDocument`, `KnowledgeChunk`, `KnowledgeEmbedding`.
* **Services Owned**: `DocumentIngestionService`, `VectorSearchService`.
* **Controllers Owned**: Internal/Admin only endpoints (**TO BE DECIDED**).
* **Repositories Owned**: `KnowledgeDocumentRepository`, `KnowledgeChunkRepository`.
* **Dependencies**: `sentrix-common`, `sentrix-storage` (for raw docs).
* **Data it may access**: Knowledge tables (pgvector).
* **Data it must not directly manipulate**: User analyses.

## 10. sentrix-analysis
* **Responsibility**: Centralized tracking of all user analyses (Fraud, Event Logs, CVEs) to facilitate history viewing and dashboard aggregation.
* **Entities Owned**: `AnalysisRecord` (abstract/metadata table).
* **Services Owned**: `AnalysisHistoryService`.
* **Controllers Owned**: `AnalysisHistoryController`.
* **Repositories Owned**: `AnalysisRecordRepository`.
* **Dependencies**: `sentrix-common`, `sentrix-session`.
* **Data it may access**: Metadata of all analysis tables (read-only for domain tables, write for its own tracking).
* **Data it must not directly manipulate**: The underlying domain logic of specific analyses.

## 11. sentrix-report
* **Responsibility**: Assembling data into PDF or JSON security reports.
* **Entities Owned**: `SecurityReport`.
* **Services Owned**: `ReportGenerationService`, `PdfFormattingService`.
* **Controllers Owned**: `ReportController`.
* **Repositories Owned**: `SecurityReportRepository`.
* **Dependencies**: `sentrix-storage`, `sentrix-analysis`, `sentrix-common`, `sentrix-session`.
* **Data it may access**: Read access to various domain analysis results via defined DTO interfaces.
* **Data it must not directly manipulate**: Original analysis records.

## 12. sentrix-dashboard
* **Responsibility**: Aggregating metrics and summary data for the frontend dashboard.
* **Entities Owned**: None.
* **Services Owned**: `DashboardAggregationService`.
* **Controllers Owned**: `DashboardController`.
* **Repositories Owned**: None.
* **Dependencies**: `sentrix-analysis`, `sentrix-common`, `sentrix-session`.
* **Data it may access**: Read access to analysis statistics.
* **Data it must not directly manipulate**: Any data.

## 13. sentrix-storage
* **Responsibility**: Abstracting file storage operations (saving EVTX files, retrieving generated PDFs).
* **Entities Owned**: `UploadedFile`.
* **Services Owned**: `FileStorageService`.
* **Controllers Owned**: `FileController` (for direct downloads).
* **Repositories Owned**: `UploadedFileRepository`.
* **Dependencies**: `sentrix-common`.
* **Data it may access**: File system or S3 compatible storage.
* **Data it must not directly manipulate**: Domain entities.

## 14. sentrix-notification (Async Processing Tracker)
* **Responsibility**: Tracking background job statuses (RabbitMQ tasks) and providing updates to the client.
* **Entities Owned**: `JobStatus` (could be Redis-backed).
* **Services Owned**: `JobTrackingService`.
* **Controllers Owned**: `StatusController`.
* **Repositories Owned**: None (Redis).
* **Dependencies**: `sentrix-common`, `sentrix-session`.
* **Data it may access**: Job tracking cache.
* **Data it must not directly manipulate**: Core DB tables.

## 15. sentrix-monitoring
* **Responsibility**: Exposing health, metrics, and actuator endpoints.
* **Entities Owned**: None.
* **Services Owned**: None.
* **Controllers Owned**: Spring Boot Actuator endpoints.
* **Repositories Owned**: None.
* **Dependencies**: `sentrix-config`.
* **Data it may access**: JVM and system metrics.
* **Data it must not directly manipulate**: Application logic.
