# Phase 14: Advanced Audit Trail

## Overview
Introduced a centralized, immutable audit log for all critical security and operational events.

## Architecture
- **Schema Update (V12):** Created `enterprise_audit_logs` table with support for JSONB metadata and fast indexing on actors, resources, and timestamps.
- **Audited Events:**
  - Login/Authentication successes and failures.
  - Authorization failures (403).
  - Creation, assignment, or resolution of Incidents and Investigations.
  - Security Action lifecycle events (Proposal, Approval, Rejection, Execution, Expiration).
  - Administrative configuration changes.

## Data Safety
- **Correlation:** Every audit log includes the `correlation_id` to trace the request across microservices and external providers.
- **Sensitive Data Masking:** The audit mechanism explicitly sanitizes payloads before writing to the database. Passwords, API keys, and raw tokens are never recorded in the `metadata` JSONB column.
