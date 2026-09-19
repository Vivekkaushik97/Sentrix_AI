# Module Boundaries

To ensure the Spring Boot application avoids becoming a monolithic tangle, the architecture is divided into the following modules (packages/conceptual boundaries):

### 1. `config`
* **Responsibility**: Spring Boot configuration, beans, properties, and external integrations setup.
* **Entities Owned**: None.
* **Services Owned**: None.
* **Dependencies**: None.

### 2. `security`
* **Responsibility**: Global security config, CORS, CSRF, exception handling for unauthorized requests, edge protection logic.
* **Entities Owned**: None.
* **Services Owned**: None.
* **Dependencies**: `config`.

### 3. `session`
* **Responsibility**: Anonymous session lifecycle, Redis integration for sessions.
* **Entities Owned**: `AnonymousSession`.
* **Services Owned**: `SessionManagementService`.
* **Dependencies**: `config`, `security`.

### 4. `common`
* **Responsibility**: Shared exceptions, base entities, utilities, and normalized DTOs (e.g., standard error responses).
* **Entities Owned**: Base entity classes.
* **Services Owned**: Utility services.
* **Dependencies**: None.

### 5. `analysis` (Core)
* **Responsibility**: Defines the common risk scoring framework, analysis history abstraction, and status tracking.
* **Entities Owned**: `AnalysisRecord`.
* **Services Owned**: `AnalysisHistoryService`.
* **Dependencies**: `common`, `session`.

### 6. `fraud`
* **Responsibility**: UPI Fraud Detection logic, Java ML inference wrapper.
* **Entities Owned**: `FraudAnalysis`, `FraudTransaction`.
* **Services Owned**: `FraudDetectionService`.
* **Dependencies**: `common`, `analysis`, `ai`.

### 7. `eventlog`
* **Responsibility**: Windows Event Log parsing, signature matching, large file handling.
* **Entities Owned**: `UploadedFile`, `LogAnalysis`, `LogEvent`, `ThreatIndicator`.
* **Services Owned**: `EventLogParserService`, `EventLogAnalysisService`.
* **Dependencies**: `common`, `analysis`, `storage`, `ai`.

### 8. `cve`
* **Responsibility**: CVE external API interaction, local caching, and vulnerability lookup.
* **Entities Owned**: `CveRecord`, `CveAnalysis`.
* **Services Owned**: `CveLookupService`.
* **Dependencies**: `common`, `analysis`, `ai`.

### 9. `ai`
* **Responsibility**: Spring AI integration, LLM configuration, prompt management, and AI orchestration.
* **Entities Owned**: `ChatSession`, `ChatMessage`.
* **Services Owned**: `CybersecurityAssistantService`, `PromptManagerService`.
* **Dependencies**: `common`, `config`.

### 10. `rag`
* **Responsibility**: Knowledge base ingestion, chunking, embedding, pgvector similarity search.
* **Entities Owned**: `KnowledgeDocument`, `KnowledgeChunk`.
* **Services Owned**: `DocumentIngestionService`, `VectorSearchService`.
* **Dependencies**: `common`, `ai`.

### 11. `report`
* **Responsibility**: Aggregating analysis results into structured security reports.
* **Entities Owned**: `SecurityReport`.
* **Services Owned**: `ReportGenerationService`.
* **Dependencies**: `common`, `analysis`, `fraud`, `eventlog`, `cve`.

### 12. `dashboard`
* **Responsibility**: Aggregating summary data for the frontend dashboard.
* **Entities Owned**: None.
* **Services Owned**: `DashboardAggregationService`.
* **Dependencies**: `common`, `analysis`, `session`.

### 13. `storage`
* **Responsibility**: Handling temporary and durable file storage (e.g., uploaded EVTX files, generated PDFs).
* **Entities Owned**: None.
* **Services Owned**: `FileStorageService`.
* **Dependencies**: `common`.

### 14. `notification` (Async)
* **Responsibility**: RabbitMQ consumers/producers, async job status tracking.
* **Entities Owned**: None.
* **Services Owned**: `AsyncJobDispatcher`, `JobStatusService`.
* **Dependencies**: `common`, `config`.

### 15. `monitoring`
* **Responsibility**: Spring Boot Actuator, Prometheus endpoints, custom metrics.
* **Entities Owned**: None.
* **Services Owned**: `HealthCheckService`.
* **Dependencies**: `config`.

**Rules:**
* Modules must not directly modify another module's database tables. They must interact via Service interfaces.
* Circular dependencies between modules are forbidden.
