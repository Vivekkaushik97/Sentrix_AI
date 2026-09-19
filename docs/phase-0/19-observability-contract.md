# Observability & Logging Contract

## Application Logging
* Use SLF4J with Logback (Spring Boot default).
* **Format**: Structured JSON logging is preferred for production to easily ingest into log aggregators.
* **Content**: 
  * Include Correlation/Request IDs via MDC (Mapped Diagnostic Context) to trace a request across services.
  * Log significant lifecycle events: Session creation, Job started, Job completed, Error encountered.
* **Security**: NEVER log passwords, session tokens, API keys, or raw PII.

## Spring Boot Actuator
* Expose `/actuator/health` to monitor application, DB, Redis, and RabbitMQ connectivity.
* Expose `/actuator/metrics` for performance tracking.
* Restrict access to Actuator endpoints; do not expose them to the public internet.

## Future Integration (Phase 20)
* **Prometheus**: Will scrape Actuator metrics (e.g., JVM memory, HTTP request times, RabbitMQ queue depths).
* **Grafana**: Will visualize Prometheus data on operational dashboards.
