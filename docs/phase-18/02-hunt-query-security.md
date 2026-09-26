# Phase 18: Hunt Query Abstraction & Security

## Overview
Secured the threat hunt query mechanism to prevent arbitrary code execution or SQL injection.

## Query Security
- **No Raw SQL:** The `query_definition` field is a validated JSONB structure mapping to safe repository-level JPA Specifications (e.g., `{"sourceType": "WINDOWS_EVENT", "timeRange": "24h"}`).
- **No External Execution:** Queries operate purely against internal datasets (Incidents, IOCs, Context). There are zero capabilities for `Runtime.exec` or autonomous arbitrary API polling within the hunt executor.
