# Column Specification

### TABLE: anonymous_sessions
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `created_at` | `TIMESTAMPTZ` | No | `now()` | - | Session creation time. |
| `last_active_at` | `TIMESTAMPTZ` | No | `now()` | - | Used for pruning idle sessions. |
| `ip_hash` | `VARCHAR` | Yes | null | - | Anonymized tracking for rate limiting (if required). |

### TABLE: analysis_records
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `session_id` | `UUID` | No | - | FK (`anonymous_sessions`) | Links to user. |
| `analysis_type` | `VARCHAR` | No | - | - | e.g., 'FRAUD', 'EVENT_LOG', 'CVE'. |
| `status` | `VARCHAR` | No | 'PENDING' | - | Lifecycle tracking. |
| `risk_score` | `INTEGER` | Yes | null | Check `0-100` | Normalized score. |
| `risk_classification` | `VARCHAR` | Yes | null | - | 'LOW', 'MEDIUM', 'HIGH', 'CRITICAL'. |
| `created_at` | `TIMESTAMPTZ` | No | `now()` | - | Creation time. |
| `updated_at` | `TIMESTAMPTZ` | No | `now()` | - | Last status update. |

### TABLE: fraud_analyses
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `analysis_record_id` | `UUID` | No | - | FK (`analysis_records`), Unique | 1:1 relationship. |
| `model_version` | `VARCHAR` | Yes | null | - | TO BE FINALIZED (ML metadata). |
| `fraud_probability` | `FLOAT` | Yes | null | Check `0.0-1.0` | Output from ML model. |
| `ai_explanation` | `TEXT` | Yes | null | - | LLM generated summary. |

### TABLE: fraud_transactions
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `fraud_analysis_id` | `UUID` | No | - | FK (`fraud_analyses`) | Parent relation. |
| `amount` | `DECIMAL` | Yes | null | - | Input feature. |
| `timestamp` | `TIMESTAMPTZ` | Yes | null | - | Input feature. |
| `features_json` | `JSONB` | Yes | null | - | Flexible storage for dynamic ML inputs (TO BE FINALIZED). |

### TABLE: uploaded_files
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `session_id` | `UUID` | No | - | FK (`anonymous_sessions`) | Ownership. |
| `filename` | `VARCHAR` | No | - | - | Original name. |
| `file_size_bytes` | `BIGINT` | No | - | - | Size limit enforcement. |
| `storage_path` | `VARCHAR` | No | - | - | Internal S3/local path. |
| `uploaded_at` | `TIMESTAMPTZ` | No | `now()` | - | Timestamp. |

### TABLE: log_analyses
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `analysis_record_id` | `UUID` | No | - | FK (`analysis_records`), Unique | 1:1 relationship. |
| `uploaded_file_id` | `UUID` | No | - | FK (`uploaded_files`) | Source file. |
| `total_events` | `INTEGER` | Yes | null | - | Log statistics. |
| `suspicious_events` | `INTEGER` | Yes | null | - | Log statistics. |
| `ai_summary` | `TEXT` | Yes | null | - | LLM generated report. |

### TABLE: threat_indicators
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `log_analysis_id` | `UUID` | No | - | FK (`log_analyses`) | Parent relation. |
| `event_id` | `VARCHAR` | No | - | - | Windows Event ID. |
| `severity` | `VARCHAR` | No | - | - | Parsed severity. |
| `description` | `TEXT` | Yes | null | - | Raw/Parsed details. |

### TABLE: cve_records
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `VARCHAR` | No | - | PK | e.g. 'CVE-2024-1234'. |
| `description` | `TEXT` | No | - | - | NVD description. |
| `cvss_score` | `FLOAT` | Yes | null | - | Vulnerability severity. |
| `published_date` | `DATE` | Yes | null | - | NVD disclosure. |
| `last_updated` | `TIMESTAMPTZ` | No | `now()` | - | Cache freshness tracking. |

### TABLE: cve_analyses
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `analysis_record_id` | `UUID` | No | - | FK (`analysis_records`), Unique | 1:1 relationship. |
| `cve_id` | `VARCHAR` | No | - | FK (`cve_records`) | Linked vulnerability. |
| `ai_explanation` | `TEXT` | Yes | null | - | Specific user context LLM output. |

### TABLE: chat_sessions
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `anonymous_session_id` | `UUID` | No | - | FK (`anonymous_sessions`) | Ownership. |
| `title` | `VARCHAR` | Yes | null | - | Auto-generated title. |
| `created_at` | `TIMESTAMPTZ` | No | `now()` | - | Creation time. |
| `updated_at` | `TIMESTAMPTZ` | No | `now()` | - | Ordering. |

### TABLE: chat_messages
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `chat_session_id` | `UUID` | No | - | FK (`chat_sessions`) | Conversation link. |
| `role` | `VARCHAR` | No | - | - | 'USER', 'ASSISTANT', 'SYSTEM'. |
| `content` | `TEXT` | No | - | - | Message body. |
| `created_at` | `TIMESTAMPTZ` | No | `now()` | - | Exact order. |

### TABLE: knowledge_documents
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `title` | `VARCHAR` | No | - | - | Document name. |
| `source_url` | `VARCHAR` | Yes | null | - | Reference URL. |
| `created_at` | `TIMESTAMPTZ` | No | `now()` | - | Ingestion time. |

### TABLE: knowledge_chunks
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `document_id` | `UUID` | No | - | FK (`knowledge_documents`) | Source doc. |
| `content` | `TEXT` | No | - | - | Split text chunk. |
| `embedding` | `VECTOR` | Yes | null | TO BE FINALIZED | pgvector data. |
| `chunk_index` | `INTEGER` | No | - | - | Order in document. |

### TABLE: security_reports
| Column | PostgreSQL Type | Nullable | Default | Key/Constraint | Purpose |
| ------ | --------------- | -------- | ------- | -------------- | ------- |
| `id` | `UUID` | No | `gen_random_uuid()` | PK | Primary identifier. |
| `session_id` | `UUID` | No | - | FK (`anonymous_sessions`) | Ownership. |
| `title` | `VARCHAR` | No | - | - | Report name. |
| `storage_path` | `VARCHAR` | Yes | null | - | Location in object store. |
| `status` | `VARCHAR` | No | 'PENDING' | - | Generation status. |
| `created_at` | `TIMESTAMPTZ` | No | `now()` | - | Trigger time. |
