# Phase 10: Redis & RabbitMQ Strategy

## Redis
- Evaluated caching `SecurityContextDto`. 
- **Decision**: NOT introducing a cache. 
- **Reasoning**: The deterministic graph resolution operates on indexed primary/foreign keys (UUIDs) across a normalized SQL schema. Fetching context is currently `O(1)` query depths. Caching would introduce invalidation complexity and stale evidence risks without tangible performance benefits right now.

## RabbitMQ
- Evaluated generating separate `ContextUpdateEvent` messages.
- **Decision**: Reuse existing `SecurityEvent` flow.
- **Reasoning**: New investigations/correlations naturally flow through the existing SSE endpoint. We do not need a parallel bus for the exact same semantic triggers. Context API remains a synchronous read-model.
