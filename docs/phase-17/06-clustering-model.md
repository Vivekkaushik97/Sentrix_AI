# Phase 17: Threat Campaign & Clustering Model

## Overview
Introduced explainable, deterministic grouping of IOCs into Threat Campaigns.

## Architecture
- **Schema (`threat_campaigns`):** Captures high-level threat objects (e.g., "LockBit 3.0").
- **Mapping (`threat_campaign_indicators`):** Explicitly maps an indicator to a campaign, enforcing the requirement of an `association_reason` (e.g., "Shared C2 infrastructure").
- **Safety:** Clustering is rule-based or manually curated by analysts. The platform DOES NOT use black-box ML to arbitrarily hallucinate associations without evidence.
