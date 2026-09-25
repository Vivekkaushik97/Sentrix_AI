# Phase 3 Database Model

This document outlines the logical and physical PostgreSQL schema implemented for Sentrix AI Phase 3.

## Core Architecture

The schema uses a centralized `analyses` table to track the high-level metadata (status, severity, type) for all security operations. Specific analysis types (fraud, event logs, CVE) extend this table using a one-to-one relationship, sharing the same `id` as a foreign key.

## Schema Details

### 1. `analyses`
The root table for all security operations.
- `id` (UUID, PK)
- `type` (VARCHAR): Enum (FRAUD, EVENT_LOG, CVE)
- `status` (VARCHAR): Enum (PENDING, PROCESSING, COMPLETED, FAILED)
- `severity` (VARCHAR): Enum (LOW, MEDIUM, HIGH, CRITICAL, NONE)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)
- `error_message` (TEXT)

### 2. `fraud_analyses`
Stores the input transaction data and the resulting risk score/findings.
- `id` (UUID, PK, FK to analyses)
- `transaction_id` (VARCHAR)
- `amount` (DECIMAL)
- `currency` (VARCHAR)
- `transaction_timestamp` (TIMESTAMP)
- `sender_info` (VARCHAR)
- `receiver_info` (VARCHAR)
- `device_info` (VARCHAR)
- `ip_address` (VARCHAR)
- `location` (VARCHAR)
- `payment_channel` (VARCHAR)
- `risk_score` (INTEGER): 0-100
- `findings` (TEXT): Rule-based findings
- `is_suspicious` (BOOLEAN)

### 3. `event_log_analyses`
Stores the results of ingested security logs.
- `id` (UUID, PK, FK to analyses)
- `log_source` (VARCHAR)
- `total_events` (INTEGER)
- `threats_detected` (INTEGER)
- `raw_log` (TEXT): The ingested log data
- `findings` (TEXT)

### 4. `cve_searches`
Caches and persists CVE query results.
- `id` (UUID, PK, FK to analyses)
- `cve_id` (VARCHAR)
- `keyword` (VARCHAR)
- `description` (TEXT)
- `cvss_score` (DECIMAL)
- `severity` (VARCHAR)
- `published_date` (TIMESTAMP)
- `raw_response` (JSONB): Raw provider response
- `findings` (TEXT)

### 5. `reports`
Stores metadata for generated security reports.
- `id` (UUID, PK)
- `analysis_id` (UUID, FK to analyses)
- `title` (VARCHAR)
- `report_type` (VARCHAR)
- `summary` (TEXT)
- `findings` (TEXT)
- `severity` (VARCHAR)
- `status` (VARCHAR)
- `created_at` (TIMESTAMP)

## Implementation Details
- Uses UUIDs for all primary keys to obscure sequential data.
- Enforces referential integrity with `ON DELETE CASCADE` for analysis subtypes.
- Created via Flyway migration `V1__init_schema.sql`.
