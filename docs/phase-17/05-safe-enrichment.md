# Phase 17: Safe Enrichment Pipeline

## Overview
Defined strict operational limits for querying intelligence providers.

## Bounded Enrichment Constraints
- **Allowed:** Reputation lookup, metadata lookup, passive DNS information, historical classification queries.
- **Strictly Prohibited:** Active port scanning, vulnerability probing, credential testing, malware execution.
- **Pipeline Integrity:** Every provider request utilizes an HTTP client configured with hard timeouts (e.g., 5 seconds) and response limits (e.g., max 1MB) to prevent denial-of-service via malformed external data.
