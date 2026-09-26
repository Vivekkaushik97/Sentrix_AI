# Phase 16: Threat Intelligence Architecture

## Overview
Defined provider-agnostic abstractions for managing Threat Intelligence (TI).

## Schema (`V14__threat_intelligence.sql`)
- `threat_intelligence_sources`: Providers or internal feeds (e.g., AlienVault, internal honeypot).
- `threat_indicators`: The actual Indicators of Compromise (IOCs). Categories supported: `IP`, `DOMAIN`, `URL`, `FILE_HASH`, `EMAIL`.
- `threat_intelligence_observations`: Point-in-time evidence that a source vouches for an indicator.
- `threat_indicator_correlations`: Many-to-many linkage between an IOC and internal entities (e.g., Incident, Investigation).

## Constraints
- Indicators are treated as passive data. Storing a malware hash does NOT execute it. Storing an IP does NOT trigger a port scan.
