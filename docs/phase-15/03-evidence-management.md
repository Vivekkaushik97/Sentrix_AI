# Phase 15: Evidence Management

## Overview
Built a centralized `evidence_records` table to securely track and reference security artifacts.

## Architecture
- **Reference Model:** `evidence_records` avoids duplicating massive payloads. It stores:
  - `source_type`: (e.g., `INCIDENT`, `ACTION_EXECUTION`, `POSTURE_SCAN`).
  - `source_id`: The UUID of the primary record.
  - `metadata`: JSONB containing cryptographic hashes, summary context, or retention limits.
- **Attribution:** Evidence captures the `collector_id` (the `SECURITY_ANALYST` or `ADMIN`) and `collected_at` timestamp.
- **Safety:** Fake evidence generation is prevented by foreign-key-like application constraints ensuring `source_id` exists in the designated source table.
