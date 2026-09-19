# Final Logical Schema

*This document consolidates the final agreed-upon table structures.*

### `anonymous_sessions`
* **Purpose**: Identity tracking.
* **PK**: `id` (UUID)

### `analysis_records`
* **Purpose**: Core risk and status tracking.
* **PK**: `id` (UUID)
* **FK**: `session_id` -> `anonymous_sessions`
* **Constraints**: `risk_score` 0-100.

### `fraud_analyses`
* **Purpose**: ML outputs.
* **PK**: `id` (UUID)
* **FK**: `analysis_record_id` -> `analysis_records` (UNIQUE)

### `fraud_transactions`
* **Purpose**: ML inputs.
* **PK**: `id` (UUID)
* **FK**: `fraud_analysis_id` -> `fraud_analyses`

### `uploaded_files`
* **Purpose**: EVTX file storage references.
* **PK**: `id` (UUID)
* **FK**: `session_id` -> `anonymous_sessions`

### `log_analyses`
* **Purpose**: Parsed log statistics.
* **PK**: `id` (UUID)
* **FK**: `analysis_record_id` -> `analysis_records` (UNIQUE), `uploaded_file_id` -> `uploaded_files`

### `threat_indicators`
* **Purpose**: Extracted malicious events.
* **PK**: `id` (UUID)
* **FK**: `log_analysis_id` -> `log_analyses`

### `cve_records`
* **Purpose**: External NVD cache.
* **PK**: `id` (VARCHAR)

### `cve_analyses`
* **Purpose**: AI context for vulnerabilities.
* **PK**: `id` (UUID)
* **FK**: `analysis_record_id` -> `analysis_records` (UNIQUE), `cve_id` -> `cve_records`

### `chat_sessions`
* **Purpose**: Conversation threads.
* **PK**: `id` (UUID)
* **FK**: `anonymous_session_id` -> `anonymous_sessions`

### `chat_messages`
* **Purpose**: Conversation content.
* **PK**: `id` (UUID)
* **FK**: `chat_session_id` -> `chat_sessions`

### `knowledge_documents`
* **Purpose**: RAG sources.
* **PK**: `id` (UUID)

### `knowledge_chunks`
* **Purpose**: RAG pgvector targets.
* **PK**: `id` (UUID)
* **FK**: `document_id` -> `knowledge_documents`

### `security_reports`
* **Purpose**: Generated PDF metadata.
* **PK**: `id` (UUID)
* **FK**: `session_id` -> `anonymous_sessions`
