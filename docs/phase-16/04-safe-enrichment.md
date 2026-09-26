# Phase 16: Safe Enrichment

## Overview
Implemented a passive threat intelligence enrichment architecture.

## Boundaries & Constraints
- **Passive Only:** Enrichment relies exclusively on querying REST APIs of configured providers (e.g., "What does Provider X know about Hash Y?").
- **Prohibited Actions:** The system MUST NOT scan hosts, probe IPs, execute files, download malware, or perform active reconnaissance.
- **Resilience:** Enrichment calls are bounded by strict HTTP timeouts, rate limits, and fallback logic to gracefully handle provider outages.
