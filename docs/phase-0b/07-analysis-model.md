# Analysis Data Model Decision

## Evaluated Options

### Option A: Completely independent analysis tables
(`fraud_analyses`, `log_analyses`, `cve_analyses` have no shared parent).
* **Pros**: Simple, highly normalized for their specific domains.
* **Cons**: The Dashboard and History page require an aggregated, paginated view of *all* recent analyses. Option A requires complex `UNION` queries across disparate tables, making sorting by `created_at` or `risk_score` extremely difficult.

### Option B: Generic `analysis_records` parent + specialized analysis tables (1:1)
(`analysis_records` holds status, risk score, session ownership; specific tables hold domain details).
* **Pros**: 
  * Simple, unified queries for the Dashboard/History (`SELECT * FROM analysis_records WHERE session_id = ? ORDER BY created_at DESC`).
  * Easily compatible with JPA `@OneToOne` mapping.
  * Extensible (adding a new module just requires a new child table linked 1:1).
* **Cons**: Requires JOINs to fetch full details.

### Option C: Single table containing all analysis types (Single Table Inheritance)
(One giant `analysis_records` table with nullable columns for every possible module feature).
* **Pros**: Avoids JOINs.
* **Cons**: Very sparse data (lots of NULLs). Bad for data integrity as it's hard to enforce NOT NULL constraints dynamically based on type.

## Decision: OPTION B
**Generic `analysis_records` parent + specialized analysis tables (1:1)**

**Reasoning**: Option B perfectly fits the documented requirement to have a "Security Risk Scoring" contract and a unified "Dashboard" and "Analysis History". It allows pagination and filtering by risk score natively via PostgreSQL, while isolating module-specific complexities in their own tables. This maps perfectly to Spring Data JPA `JOINED` inheritance or composition patterns.
