# Phase 16: Current State Audit

## Existing Architecture Overview
- **Core Platform:** Spring Boot backend with React/Vite frontend. PostgreSQL is the central, authoritative source of truth (Migrations V1–V13). Redis is used for deterministic caching, and RabbitMQ serves as an asynchronous event bus.
- **Enterprise Security (Phases 14 & 15):** The system has strong RBAC (ADMIN, SECURITY_MANAGER, SECURITY_ANALYST, VIEWER). Incidents, investigations, actions, and compliance evidence all support strict ownership, RBAC validation, and immutable auditing (`enterprise_audit_logs`).
- **Data Integrity:** All workflows strictly mandate real database records. AI is advisory and cannot bypass action approvals or compliance governance.

## Missing Capabilities for Phase 16
- **Threat Intelligence:** The platform currently relies on raw events (Windows, UPI) but lacks a centralized, provider-agnostic Threat Intelligence (TI) repository to store Indicators of Compromise (IOCs) such as IPs, domains, and file hashes.
- **Correlation:** There is no automated, deterministic correlation between an incoming security event and a known IOC.
- **Provider Abstraction:** No mechanism exists to safely query external TI feeds (e.g., VirusTotal, CrowdStrike) in a bounded, passive manner.

## Risks & Conflicts
- **External Probing:** Threat Intelligence enrichment MUST remain passive. Sentrix cannot execute malware, scan IPs, or perform credential attacks.
- **AI Boundaries:** AI cannot definitively brand an IOC as malicious without deterministic TI scoring.
- **Data Flooding:** Uncontrolled ingestion of external threat feeds could overwhelm the database. The system needs clear IOC normalization and deduplication.

## Implementation Strategy
- Create Migration `V14__threat_intelligence.sql` to model TI sources, indicators, observations, and correlations safely without modifying V1–V13.
- Introduce `ThreatIntelligenceService` and `ThreatIntelligenceProvider` abstractions.
