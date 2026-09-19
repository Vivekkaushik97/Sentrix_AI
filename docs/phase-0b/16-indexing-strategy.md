# Indexing Strategy

To maintain performance, B-Tree indexes (or HNSW for vectors) will be applied to specific query paths.

### 1. Session Lookups
* **Index**: `analysis_records (session_id, created_at DESC)`
* **Reason**: Dashboard and History queries will always filter by session and sort by date.

### 2. Risk Filtering
* **Index**: `analysis_records (risk_classification)`
* **Reason**: Future frontend filters (e.g., "Show me only CRITICAL alerts").

### 3. CVE Lookups
* **Index**: `cve_records (id)` (Provided by PK).

### 4. Vector Similarity (pgvector)
* **Index**: `knowledge_chunks (embedding vector_cosine_ops)` using HNSW (Hierarchical Navigable Small World).
* **Reason**: Required by pgvector to perform fast approximate nearest neighbor (ANN) searches instead of full table scans during RAG.

**Note**: Foreign keys should generally have indexes created for them if they are heavily queried (e.g., `chat_messages.chat_session_id`).
