# Phase 13: Health, Readiness, and Liveness

## Overview
Hardened the Spring Boot Actuator configuration to provide safe production endpoints for container orchestration platforms (like Docker Swarm or Kubernetes) without exposing internal sensitive details.

## Hardening Steps
1. **Liveness vs. Readiness:**
   - Enabled Kubernetes/Probes support in `application-prod.yml` via `management.endpoint.health.probes.enabled: true`.
   - `/actuator/health/liveness` confirms the application process is running and not deadlocked.
   - `/actuator/health/readiness` confirms the application is connected to its critical dependencies (PostgreSQL, Redis, RabbitMQ) and is ready to serve traffic.

2. **Security:**
   - Ensured `show-details` is set to `when_authorized` (or `never` if anonymous) in production to avoid leaking database URLs, Redis nodes, and RabbitMQ exchanges to unauthenticated actors.
   - All other actuator endpoints (env, metrics, info, threads, dump) are hidden from web exposure in the production profile.
