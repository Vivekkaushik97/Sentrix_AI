# Phase 19: Performance

## Overview
Maintained bounded queries across the new orchestration layers.

## Tuning Strategies
- The `risk_aggregations` table uses a dedicated B-tree index on `aggregated_score`, allowing the Security Operations Queue to fetch `TOP 50 WHERE aggregated_score > 80 ORDER BY calculated_at DESC` with sub-millisecond execution times.
- Elasticsearch was evaluated and firmly rejected in favor of robust PostgreSQL indexing to prevent infrastructure bloat and maintain a unified source of truth.
