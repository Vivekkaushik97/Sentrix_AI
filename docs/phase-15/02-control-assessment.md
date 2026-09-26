# Phase 15: Control Assessment Engine

## Overview
Implemented a deterministic engine to assess control states based purely on attached platform evidence.

## Logic
- **Statuses:** `PASS`, `FAIL`, `PARTIAL`, `NOT_ASSESSED`, `NOT_APPLICABLE`.
- **Determinism Rule:** 
  - If a control has zero linked evidence, its state evaluates to `NOT_ASSESSED` or `FAIL`. It can NEVER hallucinate a `PASS`.
  - Evaluations require either automated rules tracing back to internal metrics (e.g., action approval logs) or explicit human assertion backed by an audit trail.
- **Explainability:** When the AI explains an assessment, it strictly references the retrieved `evidence_records` metadata. It is prohibited from inventing a justification without a primary data source.
