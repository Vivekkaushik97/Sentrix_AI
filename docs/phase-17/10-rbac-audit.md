# Phase 17: RBAC, Governance & Auditability

## Authorization Models
- Introduced fine-grained roles mapped to Phase 14 foundations: `THREAT_INTEL_VIEW`, `THREAT_INTEL_CREATE`, `THREAT_INTEL_UPDATE`, `THREAT_INTEL_SUPPRESS`, `THREAT_FEED_MANAGE`, `THREAT_CAMPAIGN_MANAGE`.

## Audit Logging
- Every single alteration to an IOC (e.g., marking as `FALSE_POSITIVE`) or Campaign generates a detailed log in `enterprise_audit_logs`.
- External feed errors or misconfigurations are logged, strictly scrubbing any potential secrets, passwords, or Authorization tokens.
