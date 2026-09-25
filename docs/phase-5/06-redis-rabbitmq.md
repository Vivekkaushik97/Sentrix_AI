# Phase 5: Redis and RabbitMQ Integration

## Redis Usage
- **Current Role**: Redis is successfully utilized for caching `CveSearch` lookups (Phase 4), reducing API calls to NVD. It also handles Spring Session data.
- **AI Extension**: Redis is the ideal candidate for caching `RetrievalResult` and semantic embeddings if a heavy external EmbeddingProvider is used, avoiding repeated token costs for identical queries. Currently, this is deferred until an external LLM is configured.

## RabbitMQ Evaluation
- **Current Role**: RabbitMQ config is established.
- **Phase 5 Evaluation**: Introducing RabbitMQ queues for AI/RAG document chunk ingestion makes architectural sense when processing massive logs asynchronously. However, forcing RabbitMQ into the current synchronous mock AI workflow provides no tangible benefit and adds unnecessary complexity.
- **Decision**: Deferred async ingestion to a future production workload phase. Synchronous processing remains for Phase 5 to adhere to the "do not over-engineer" guideline.
