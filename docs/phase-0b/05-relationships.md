# Relationships

### Anonymous Session → Analysis Records
* **Parent**: `anonymous_sessions`
* **Child**: `analysis_records`
* **Cardinality**: 1:N
* **Foreign Key**: `session_id` (Required)
* **Delete Behavior**: CASCADE. If a session is pruned, its history goes with it.
* **Reason**: Strict data isolation per anonymous user.

### Analysis Records → Specific Analysis (Fraud/Log/CVE)
* **Parent**: `analysis_records`
* **Child**: `fraud_analyses`, `log_analyses`, `cve_analyses`
* **Cardinality**: 1:1
* **Foreign Key**: `analysis_record_id` (Required, UNIQUE)
* **Delete Behavior**: CASCADE.
* **Reason**: Separates the unified dashboard tracking data from the domain-specific ML/Parsing output data.

### Fraud Analysis → Fraud Transactions
* **Parent**: `fraud_analyses`
* **Child**: `fraud_transactions`
* **Cardinality**: 1:1 or 1:N (Depending on dataset - likely 1:1 per analysis request).
* **Foreign Key**: `fraud_analysis_id` (Required)
* **Delete Behavior**: CASCADE.
* **Reason**: Stores the input features used for inference to allow reproducibility.

### Uploaded File → Log Analysis
* **Parent**: `uploaded_files`
* **Child**: `log_analyses`
* **Cardinality**: 1:1
* **Foreign Key**: `uploaded_file_id` (Required)
* **Delete Behavior**: SET NULL / RESTRICT.
* **Reason**: Connects the parsed log metadata to the raw file that generated it.

### Log Analysis → Threat Indicators
* **Parent**: `log_analyses`
* **Child**: `threat_indicators`
* **Cardinality**: 1:N
* **Foreign Key**: `log_analysis_id` (Required)
* **Delete Behavior**: CASCADE.
* **Reason**: A single Event Log file may contain hundreds of discrete suspicious events.

### CVE Record → CVE Analysis
* **Parent**: `cve_records`
* **Child**: `cve_analyses`
* **Cardinality**: 1:N
* **Foreign Key**: `cve_id` (Required)
* **Delete Behavior**: RESTRICT (Do not delete global cache if a user deletes their analysis).
* **Reason**: Normalizes the NVD external data so multiple users analyzing the same CVE share the same underlying cached details.

### Chat Session → Chat Messages
* **Parent**: `chat_sessions`
* **Child**: `chat_messages`
* **Cardinality**: 1:N
* **Foreign Key**: `chat_session_id` (Required)
* **Delete Behavior**: CASCADE.
* **Reason**: Standard chat structure.

### Knowledge Document → Knowledge Chunks
* **Parent**: `knowledge_documents`
* **Child**: `knowledge_chunks`
* **Cardinality**: 1:N
* **Foreign Key**: `document_id` (Required)
* **Delete Behavior**: CASCADE.
* **Reason**: RAG pipeline chunking.

### Anonymous Session → Security Reports
* **Parent**: `anonymous_sessions`
* **Child**: `security_reports`
* **Cardinality**: 1:N
* **Foreign Key**: `session_id` (Required)
* **Delete Behavior**: CASCADE.
* **Reason**: Links generated PDFs to the requesting user.
