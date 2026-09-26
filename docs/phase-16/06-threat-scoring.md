# Phase 16: Threat Confidence Engine

## Overview
Created a deterministic scoring mechanism (0–100) for IOCs based on empirical observations.

## Scoring Formula Inputs
- **Source Reliability:** Base score modifier determined by the `threat_intelligence_sources.reliability_score`.
- **Corroboration:** Score increases based on the number of independent sources observing the same indicator.
- **Recency:** Score degrades over time based on the delta between `last_seen` and `CURRENT_TIMESTAMP`.
- **Safety:** The AI MUST NOT arbitrarily determine the authoritative score. The score is strictly computed via backend logic.
