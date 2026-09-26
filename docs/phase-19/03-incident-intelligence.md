# Phase 19: Incident Intelligence Correlation

## Overview
Hardened the correlation between raw security incidents and the threat intelligence layer.

## Mechanism
- **Factual Mapping:** When a Windows Event spawns an Incident, the backend passively queries the `threat_indicators` table. If an exact match (e.g., matching IPv4) exists, a deterministic link is created.
- **Constraints:** AI is explicitly prevented from inserting "likely" or "inferred" incident correlations. Matches must be 1:1 factual data overlaps.
