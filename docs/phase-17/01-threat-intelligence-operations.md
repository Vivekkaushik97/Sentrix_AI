# Phase 17: Threat Intelligence Operations Architecture

## Overview
Defined the operational workflows for managing the lifecycle, clustering, and ingestion of Threat Intelligence.

## Data Flow
- **Ingestion:** `ThreatFeed` configurations synchronize raw observables from `ThreatFeedProvider` interfaces.
- **Enrichment:** Passive lookup strategies gather historical metadata without active reconnaissance.
- **Lifecycle (IOC):** IOCs transition through deterministic states (`NEW` -> `ACTIVE` -> `EXPIRED` / `FALSE_POSITIVE`).
- **Clustering:** Explainable `ThreatCampaign` entities group related IOCs using shared classifications (e.g., `RANSOMWARE`).
- **Analyst Workspace:** A Threat Hunting UI surfaces clustered data, integrating seamlessly with the `SecurityContext` and the AI assistant.
