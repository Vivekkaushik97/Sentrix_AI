# 17 - Final Foundation Architecture

## Backend
- **Framework**: Spring Boot 3.2.x, Java 25 LTS, Maven.
- **Data**: Data JPA & PostgreSQL dependencies configured. Flyway is enabled but contains NO migrations.
- **Messaging/Cache**: AMQP and Data Redis configured to target localhost endpoints.
- **Security**: Stateless/Anonymous foundation. CSRF enabled via CookieCsrfTokenRepository (excluding health check/actuator). No domain security logic.
- **API**: `/api/v1/health` provides foundation liveness and exposes the standardized `ApiResponse` envelope.

## Frontend
- **Framework**: React, Vite, TypeScript.
- **Styling**: Tailwind CSS (v3.4) and shadcn/ui.
- **Scope**: Skeletal routes. Connectivity checks against the backend health API via Axios.

## Infrastructure
- **Docker**: Local Redis (7) and RabbitMQ (3.13-management) instances.
- **CI**: GitHub Actions workflow for parallel backend and frontend compilation validation.
