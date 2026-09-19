# Identifiers and Timestamps

## Identifier Strategy
* **Primary Strategy**: `UUIDv4`.
* **Database Generation**: Handled natively by PostgreSQL using `gen_random_uuid()`.
* **Public IDs**: Internal UUIDs will be exposed directly to the frontend. No integer sequential IDs will be used to prevent enumeration attacks (IDOR).
* **Exceptions**: `cve_records` will use the actual CVE string (e.g., `CVE-2024-1234`) as the primary key since it is naturally globally unique and universally recognized.

## Timestamp Conventions
* **Format**: All temporal columns use `TIMESTAMPTZ` (Timestamp with Time Zone) to enforce UTC storage and correct local conversions.
* **Tracking**:
  * `created_at`: The exact time a row was initially persisted. Defaults to `now()`.
  * `updated_at`: The time the row was last modified (e.g., `analysis_records` changing from PENDING to COMPLETED).
  * `last_active_at`: Specific to `anonymous_sessions` for pruning logic.
