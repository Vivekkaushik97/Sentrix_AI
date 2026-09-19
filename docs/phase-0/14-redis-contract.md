# Redis Responsibilities Contract

Redis is used strictly for ephemeral state, caching, and coordination. It is NOT the permanent source of truth.

## What Belongs in Redis
* **Anonymous Sessions**: UUIDs and TTL management for active users.
* **Rate Limiting**: Request counters per Session ID or IP address to prevent abuse.
* **Temporary Processing State**: Ephemeral status updates for async jobs (e.g., "Processing 50%", "Validating file") before they are finalized.
* **Cache Entries**: Highly accessed, rarely changing external data (e.g., caching a CVE response for 1 hour to reduce external API calls).

## What Belongs in PostgreSQL
* **Durable Analysis Results**: Fraud outputs, parsed threat indicators.
* **Chat History**: Full conversation transcripts.
* **Knowledge Base**: RAG documents, chunks, and pgvector embeddings.
* **Application Records**: Final state of long-running jobs, historical logs.

**Rule**: If Redis goes down and its data is wiped, active users will be logged out and lose current in-flight async jobs, but past analyses, chat histories, and the RAG knowledge base MUST remain safely intact in PostgreSQL.
