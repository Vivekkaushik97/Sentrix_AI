# Phase 16: Threat Correlation

## Overview
Established deterministic correlation between IOCs and internal security events.

## Mechanism
- **Schema:** `threat_indicator_correlations` links `indicator_id` to `entity_type` (e.g., `INCIDENT`) and `entity_id`.
- **Logic:** When a Windows Event or UPI transaction is logged containing an IP, the system passively checks if that IP exists in `threat_indicators`. If matched, a correlation record is saved.
- **Safety:** No fake relationships are hallucinated. Correlations are drawn purely from exact substring or normalized string matches against factual data.
