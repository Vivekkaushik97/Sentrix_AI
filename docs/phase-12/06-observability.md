# Observability & Tracing

## Objective
Establish a lightweight, production-ready observability and monitoring layer for Sentrix AI without introducing heavy external APM dependencies unprompted.

## Existing Capabilities (Audited)
- **Correlation IDs**: `CorrelationIdFilter` intercepts incoming requests, generates/extracts a UUID, and binds it to SLF4J MDC.
- **Global Exception Handling**: `GlobalExceptionHandler` manages REST error mappings and utilizes `ApiErrorResponse` DTOs.
- **Spring Boot Actuator**: Included in `application.yml`, exposing `/actuator/health`, `/info`, and `/metrics`.

## Enhancements
- **Actuator Security**: Explicitly verified that sensitive endpoints (`env`, `heapdump`, `configprops`) are excluded from web exposure.
- **Structured Logging**: Logback patterns in `application.yml` inject `[%X{correlationId}]` natively.
- **Metrics**: Standard JVM, HTTP, and connection pool metrics are tracked via Micrometer.

## Security Constraints
No application properties, secrets, or API tokens are leaked through any health or observability endpoint. Stack traces are masked behind standard 500 API responses.
