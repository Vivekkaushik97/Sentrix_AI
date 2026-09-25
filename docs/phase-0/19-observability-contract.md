# Phase 0: Logging & Observability Contract

This document outlines the observability strategy for Sentrix AI, ensuring system health can be monitored and issues debugged efficiently. Implementation of monitoring tools is deferred to Phase 20.

## 1. Application Logging

### Framework
*   **SLF4J + Logback**: The standard Spring Boot logging framework.

### Log Levels
*   **ERROR**: System failures requiring immediate attention (e.g., Database connection loss, RabbitMQ failure, uncaught exceptions).
*   **WARN**: Recoverable issues, unexpected but non-fatal conditions, or rate-limit triggers.
*   **INFO**: Significant lifecycle events (e.g., Application started, job completed successfully, new CVE fetched). Do NOT log every HTTP request payload at this level.
*   **DEBUG/TRACE**: Detailed execution paths (e.g., SQL query logging, precise parsing steps for EVTX). Used only in development or targeted troubleshooting.

### Security and Sanitization
*   **NEVER LOG**: Passwords, API Keys (LLM, CVE), Session IDs in plaintext, or raw PII from transaction data or chat messages.
*   **Masking**: Implement log masking for sensitive data if it must be logged.

### Correlation (MDC)
*   **Mapped Diagnostic Context (MDC)**: Every log entry generated during an HTTP request or RabbitMQ job execution MUST include a `requestId` and/or `jobId`. This allows tracing a single transaction across multiple log lines.

## 2. Metrics (Spring Boot Actuator)

Spring Boot Actuator will be used to expose internal application metrics.

### Key Endpoints
*   `/actuator/health`: Basic UP/DOWN status of the application, including health indicators for PostgreSQL, Redis, and RabbitMQ.
*   `/actuator/metrics`: Detailed JVM metrics (memory, garbage collection, threads) and application metrics (HTTP request durations, database connection pool stats).
*   `/actuator/prometheus`: Exposes metrics in a format scrapeable by Prometheus.

### Custom Metrics
The application should define custom metrics for critical business operations:
*   `sentrix.fraud.analysis.count` (Counter)
*   `sentrix.evtx.upload.size` (Distribution Summary)
*   `sentrix.cve.api.latency` (Timer)
*   `sentrix.ai.prompt.tokens` (Counter)
*   `sentrix.jobs.queue.size` (Gauge)

## 3. Future Monitoring Infrastructure (Phase 20)
*   **Prometheus**: Will be configured to scrape the `/actuator/prometheus` endpoint at regular intervals to store time-series metric data.
*   **Grafana**: Will connect to Prometheus to visualize metrics on custom dashboards (e.g., "System Health", "API Latency", "Analysis Throughput").
*   **Alerting**: Prometheus Alertmanager will be used to trigger notifications (e.g., Slack/Email) if critical thresholds are breached (e.g., Error rate > 5%, Queue length > 100).
