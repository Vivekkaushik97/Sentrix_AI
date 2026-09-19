# Entity Inventory

Evaluation of candidate entities from Phase 0:

### Session
* **`anonymous_sessions`**: **Keep**. Essential for correlating history, chats, and reports to a user without requiring PII.

### Core Analysis
* **`analysis_records`**: **Keep (as a core table)**. Serves as a centralized record for the dashboard and history endpoints to easily query generic risk scores and statuses across all analysis types.

### Fraud
* **`fraud_analyses`**: **Keep**. Holds ML probability and AI explanation specific to fraud. (1:1 with `analysis_records`).
* **`fraud_transactions`**: **Keep**. Stores the input data required for analysis reproducibility.

### Event Logs
* **`uploaded_files`**: **Keep**. Tracks raw files before they are processed by the worker.
* **`log_analyses`**: **Keep**. Specific metadata for event log parsing (1:1 with `analysis_records`).
* **`threat_indicators`**: **Keep**. Individual suspicious events extracted from the logs (1:N with `log_analyses`).

### CVE
* **`cve_records`**: **Keep**. Acts as a local durable cache for external NVD data to avoid aggressive rate limiting.
* **`cve_analyses`**: **Keep**. Links a user's `analysis_record` to a specific `cve_record`.

### AI
* **`chat_sessions`**: **Keep**. Groups related messages into a conversation thread.
* **`chat_messages`**: **Keep**. Stores chronological interactions.

### RAG
* **`knowledge_documents`**: **Keep**. Tracks ingested files/URLs.
* **`knowledge_chunks`**: **Keep**. Stores text chunks and holds the pgvector embedding.

### Reports
* **`security_reports`**: **Keep**. Stores metadata and external object storage references for generated PDFs.

**Conclusion**: All candidate entities from Phase 0 represent distinct, normalized concepts and have been retained.
