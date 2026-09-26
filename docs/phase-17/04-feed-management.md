# Phase 17: Threat Feed Management

## Overview
Introduced the `threat_feeds` schema to orchestrate the synchronous ingestion of threat intelligence.

## Mechanisms
- **Tracking:** Each feed maintains `last_sync_time`, `sync_interval_minutes`, and `error_state`.
- **Secrets:** The database STRICTLY omits API keys or credentials. Connections authenticate via OS environment variables injected into the specific `ThreatFeedProvider` beans.
