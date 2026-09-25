# Phase 8: Redis & RabbitMQ

## RabbitMQ
- Used for high-throughput, asynchronous decoupling of ingestion (API Gateway) from analysis engines (Fraud, Windows).
- Provides robustness under load bursts.

## Redis
- Already configured as a generic Spring Session store.
- **Future Use**: Caching dashboard aggregate metrics or rate limiting the `/ingest` API endpoints. Not used as a source of transactional truth (PostgreSQL retains that role).
