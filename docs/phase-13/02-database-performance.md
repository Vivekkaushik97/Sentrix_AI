# Phase 13: Database Performance Review

## Overview
Reviewed database tables and queries to ensure optimal performance, particularly for frequently accessed collections.

## Findings
- `security_incidents` and `investigations` tables lack indices on `status` and `created_at`, which are heavily used in dashboard queries and timeline views.
- `investigation_events` and `investigation_notes` lacked foreign key indices, leading to potential full table scans on cascading deletes or specific investigation lookups.
- Did not introduce excessive indexes to maintain fast write performance.

## Actions Taken
1. **Created Migration `V9__performance_indexes.sql`:**
   - Added index on `security_incidents(status)` and `security_incidents(created_at)`.
   - Added index on `investigations(status)` and `investigations(created_at)`.
   - Added index on `investigation_events(investigation_id)`.
   - Added index on `investigation_notes(investigation_id)`.
   
2. **N+1 Mitigation:**
   - Ensured that existing data loading patterns do not overly rely on eager fetching for non-essential relations.
