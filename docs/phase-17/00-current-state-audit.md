# Phase 17: Current State Audit

## Existing Intelligence Architecture (Phase 16 Baseline)
- **IOC Entities:** `threat_indicators` and `threat_intelligence_observations` established in `V14`. IOCs are purely static representations.
- **Provider Abstractions:** `ThreatIntelligenceProvider` is in place, enabling basic integration with external APIs safely (passive only).
- **Correlation:** Simple string-matching correlation exists (`threat_indicator_correlations`) but lacks campaign-level clustering.
- **Scoring & Context:** Threat scoring is deterministic. IOCs integrate into the Phase 10 `SecurityContext`. AI reasoning boundaries prevent hallucination of IOC reputation.

## Operational Gaps for Phase 17
- **Lifecycle Management:** IOCs lack state-machines (e.g., `NEW`, `ACTIVE`, `FALSE_POSITIVE`). They cannot be deterministically suppressed or expired.
- **Feed Management:** No automated tracking of external feed health, synchronization intervals, or provider-level errors exists.
- **Campaign Clustering:** No mechanism exists to cluster indicators into broader threat campaigns (e.g., associating IP X and Domain Y to "Campaign Z").
- **Hunting Workspace:** Analysts lack a dedicated UI workflow for executing threat hunts across clustered campaigns and enrichments.

## Security & Audit Baselines
- Existing Phase 14 RBAC (`THREAT_INTEL_VIEW`, `THREAT_INTEL_UPDATE`) provides a foundation.
- Phase 14 `enterprise_audit_logs` will be the destination for tracking IOC lifecycle state changes.

## Next Steps
Introduce migration `V15__threat_intelligence_operations.sql` to manage IOC lifecycle states, Threat Feeds, and Threat Campaigns without mutating `V14`.
