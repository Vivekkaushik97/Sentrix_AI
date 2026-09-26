# Phase 19: Data Integrity Audit

## Overview
Confirmed that V17 preserves Phase 1–18 integrity.

## Validation
- **Immutability:** The `risk_aggregations` table calculates state at a point in time. Recalculations issue a new version of the entity rather than deleting the historical record (or simply use an `UPDATE` that generates an audit trail).
- **Migration Sequencing:** Validated that V1–V16 exist, are unmodified, and correctly pass Flyway checksums. V17 is sequentially applied.
- **Foreign Keys:** `risk_aggregations` correctly uses generic but strictly-typed `entity_type` and `entity_id` mappings (since it applies across multiple tables like Incident/Investigation).
