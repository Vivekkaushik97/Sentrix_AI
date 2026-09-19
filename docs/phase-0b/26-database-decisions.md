# Database Decision Records (ADRs)

## DB-001 — Supabase PostgreSQL
* **Decision**: Use Supabase managed PostgreSQL.
* **Context**: Need a reliable RDBMS.
* **Reason**: Robust, offers pgvector, free tier for Capstone.

## DB-002 — pgvector inside PostgreSQL
* **Decision**: Keep vector embeddings in PG via `pgvector` rather than Pinecone/Weaviate.
* **Reason**: Decreases architectural moving parts. High compatibility with Spring AI.

## DB-003 — UUID strategy
* **Decision**: All primary keys (except external CVE IDs) use `UUIDv4`.
* **Reason**: Prevents ID enumeration. Built natively in PG.

## DB-004 — Analysis data model
* **Decision**: Unified `analysis_records` table linked 1:1 to specific domain tables.
* **Reason**: Best balance of query simplicity for the dashboard and normalized schema integrity.

## DB-005 — Anonymous session persistence
* **Decision**: Durable tracking in PG, active TTL in Redis.
* **Reason**: Redis ensures fast auth checks; PG ensures history survives Redis restarts.

## DB-006 — Report storage strategy
* **Decision**: PostgreSQL stores only metadata and file paths; actual PDF binaries are stored on disk/S3.
* **Reason**: Storing large binaries in the DB causes bloat and performance degradation.

## DB-007 — Raw event-log storage strategy
* **Decision**: Raw `.evtx` events are NOT saved to PostgreSQL. Only aggregate stats and `threat_indicators` are saved.
* **Reason**: Prevents massive database bloat from benign OS events.

## DB-008 — Migration strategy
* **Decision**: Use Flyway.
* **Reason**: Standard Spring Boot integration, automated application of SQL scripts.
