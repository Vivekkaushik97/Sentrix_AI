# Database Responsibilities

## Data Storage Boundaries

| Data/State | PostgreSQL | Redis | RabbitMQ | pgvector (PG Extension) |
| ---------- | ---------- | ----- | -------- | -------- |
| Anonymous sessions (History) | Yes (Durable) | No | No | No |
| Anonymous sessions (Active/TTL) | No | Yes (Cache) | No | No |
| Durable analysis results | Yes | No | No | No |
| Temporary analysis state | No | Yes | No | No |
| Chat history | Yes | No | No | No |
| CVE records (Cache/Local DB) | Yes | No | No | No |
| Fraud results | Yes | No | No | No |
| Uploaded-file metadata | Yes | No | No | No |
| Report metadata | Yes | No | No | No |
| Report content (PDF) | No (S3/Object Storage) | No | No | No |
| RAG documents | Yes | No | No | No |
| RAG chunks (Text) | Yes | No | No | No |
| RAG Embeddings | No | No | No | Yes |
| Job messages | No | No | Yes | No |
| Cache entries (API lookups) | No | Yes | No | No |
| Job status tracking | Yes (Final), Redis (In-flight) | Yes (In-flight) | No | No |

### Principles
* **DURABLE DATA**: Stored in PostgreSQL. Source of truth for history and reporting.
* **TEMPORARY DATA / CACHE**: Stored in Redis. Fast access, disposable.
* **MESSAGE/JOB DATA**: Stored in RabbitMQ. Ephemeral queues.
* **VECTOR DATA**: Stored in pgvector for similarity search.
