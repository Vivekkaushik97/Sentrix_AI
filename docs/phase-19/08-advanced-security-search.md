# Phase 19: Advanced Security Search

## Overview
Extended the global search bar to cover new orchestrations.

## Supported Entities
- Phase 19 Search supports querying exact IOCs, Campaign Identifiers, and Incident UUIDs simultaneously.
- **Backend Approach:** Instead of a generic `LIKE %search%` across a billion rows or relying on Elasticsearch, we utilize explicit indexed exact-match searches per repository, aggregating the limited top-10 hits. This prevents DB CPU exhaustion.
