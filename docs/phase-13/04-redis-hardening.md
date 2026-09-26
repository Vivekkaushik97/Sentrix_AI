# Phase 13: Redis Production Readiness

## Overview
Reviewed Redis caching strategy to ensure it operates purely as an ephemeral performance layer, without compromising PostgreSQL as the system of record.

## Hardening Steps
1. **Explicit Caching Candidates:**
   - Caching is strictly limited to deterministic, read-heavy data.
   - Allowed: CVE details, static reference data, and some deterministic AI prompt context lookups.
   - Prohibited: Security action statuses, approval state, incident status, investigation states.

2. **TTL Configuration:**
   - Ensured that any `@Cacheable` or manual Redis template operations have explicit Time-To-Live (TTL) configured.
   - This prevents stale data propagation and unbounded memory growth in the Redis instance.

3. **Graceful Fallback:**
   - The application relies on Spring Cache abstractions which can be configured or intercepted to fall back to PostgreSQL upon Redis connection failure.
   - Redis failure will not destroy the application's overall availability, only degrade performance slightly on cacheable reads.

4. **Session Storage:**
   - Spring Session is backed by Redis (configured in `application-prod.yml`), ensuring horizontally scalable authentication states without tying sessions to a single backend node.
