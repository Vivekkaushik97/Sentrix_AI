# Production Readiness Audit

## 1. Backend Configuration & Infrastructure

### 1.1 Application Configuration
- **Database Connection Pooling:** Currently using default HikariCP settings. There are no explicit production boundaries (e.g., `maximum-pool-size`, `minimum-idle`, `connection-timeout`) defined in `application.yml`.
- **CORS Configuration:** `WebMvcConfig.java` currently hardcodes `.allowedOrigins("http://localhost:5173")`. This is fundamentally a development configuration and violates production deployment practices where origins should be driven by environment variables (e.g., `CORS_ALLOWED_ORIGINS`).
- **Profiles:** Missing distinct `application-prod.yml` profile.

### 1.2 Observability & Exception Handling
- **Correlation IDs:** A `CorrelationIdFilter` exists and is properly injected into MDC, which reflects in logging patterns `%X{correlationId}`.
- **Exception Handling:** `GlobalExceptionHandler` intercepts basic errors. Needs verification that stack traces are suppressed in non-dev profiles.

### 1.3 Messaging & Caching
- **RabbitMQ & Redis:** Connections are parameterized in `application.yml`, which is production-ready. However, resilience settings (retries, dead-letter exchanges) for RabbitMQ need hardening in Step 9.

## 2. Frontend Security & Configuration
- **Environment Variables:** Minimal configuration. Needs dedicated `.env.production` mappings.
- **API Error Handling:** Exists using basic Try/Catch and Sonner Toasts. Needs global interceptor to handle 401s (Unauthorized), 403s (Forbidden), and 503s cleanly.

## 3. Security
- **Authentication Boundaries:** `SecurityConfig.java` relies on basic, simplistic configurations (potentially InMemoryUserDetails). Needs proper integration or lockdown if placed behind an API Gateway in production.
- **Secrets Management:** Environment variables are properly externalized via `.env`. No hardcoded sensitive secrets found in `application.yml`.

## Recommendations for Phase 12
1. Externalize CORS allowed origins to environment variables.
2. Ensure HikariCP configuration is tightened.
3. Suppress stack traces for API Error Responses automatically.
4. Establish concrete Dead Letter Queues for RabbitMQ.
