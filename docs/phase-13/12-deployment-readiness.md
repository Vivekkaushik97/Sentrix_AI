# Phase 13: Deployment Readiness

## Overview
Ensured the application is packaged and documented for safe production deployment using standard orchestration or Docker workflows.

## Deployment Checklist
1. **Infrastructure Provisioning:**
   - PostgreSQL (Primary system of record).
   - Redis (Session and caching layer).
   - RabbitMQ (Event bus).
   - *Ensure these are network-isolated and require authentication.*

2. **Backend Configuration:**
   - Compile using `./mvnw clean verify`.
   - Run the jar with `SPRING_PROFILES_ACTIVE=prod`.
   - Inject required environment variables (`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_PASSWORD`, `CORS_ALLOWED_ORIGINS`, etc.).
   - Flyway migrations will run automatically on startup to reach V9 schema.

3. **Frontend Configuration:**
   - Build using `npm run build`.
   - Serve the resulting `dist/` directory via a web server (Nginx/Caddy) or CDN.
   - Configure reverse proxy routing from `/api` to the backend service.

4. **Health Checks:**
   - Configure orchestration load balancers to poll `/actuator/health/liveness` and `/actuator/health/readiness`.
