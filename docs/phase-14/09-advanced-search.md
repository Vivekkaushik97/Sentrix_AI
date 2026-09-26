# Phase 14: Advanced Search

## Overview
Enhanced the Unified Security Search to support structured filtering across operational dimensions without introducing the overhead of a dedicated search cluster (e.g., Elasticsearch).

## Capabilities
- **Structured Filters:** Search APIs now support dynamic filters including:
  - Date Ranges (`start_time`, `end_time`)
  - Status (`OPEN`, `RESOLVED`, `EXPIRED`)
  - Severity and Priority bounds
  - Assigned Analyst (`assigned_analyst_id`)
  - Resource Source (e.g., Windows Events vs. UPI Transactions)

## Architecture
- Leveraged Spring Data JPA `Specification` or Native PostgreSQL queries to build dynamic `WHERE` clauses efficiently.
- Utilized the indices created in Phase 13 (`V9__performance_indexes.sql`) and Phase 14 (`V12__enterprise_audit.sql`) to ensure multi-filter queries remain highly performant.
- Avoided importing Elasticsearch since the volume of real-time security events is handled comfortably by PostgreSQL with appropriate table partitioning and indexing strategies.
