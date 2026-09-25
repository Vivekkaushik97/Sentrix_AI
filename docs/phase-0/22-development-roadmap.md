# Phase 0: Development Roadmap

This document outlines the sequential phases for implementing Sentrix AI. Subsequent phases depend on the successful completion and verification of prior phases.

## Phase 1 — Foundation
*   **Prerequisites**: Phase 0 completion.
*   **Outputs**: Initial Spring Boot project (Java 25 LTS), React + Vite project setup, basic repository structure, `.env` loading, health endpoints.
*   **Dependencies**: None.
*   **Verification**: Backend returns 200 OK on `/actuator/health`; Frontend renders a blank page on localhost.

## Phase 2 — UI/UX Design System
*   **Prerequisites**: Phase 1.
*   **Outputs**: Tailwind + shadcn/ui configured, core components (buttons, cards, layout, sidebar) built, global routing setup (empty pages).
*   **Dependencies**: React, Tailwind.
*   **Verification**: Frontend matches the dark-mode aesthetic described in the UI/UX contract.

## Phase 3 — Database Implementation
*   **Prerequisites**: Phase 1.
*   **Outputs**: Supabase PostgreSQL instance provisioned, Flyway/Liquibase migrations for core tables, Spring Data JPA entities created.
*   **Dependencies**: Supabase.
*   **Verification**: Integration tests successfully read/write to the test database schema.

## Phase 4 — Security + Anonymous Sessions
*   **Prerequisites**: Phase 2, Phase 3.
*   **Outputs**: Redis configured, Spring Security filter chain, session initialization endpoint, frontend session interceptor.
*   **Dependencies**: Redis.
*   **Verification**: Frontend automatically receives and stores a `SESSION_ID` cookie; backend rejects unauthorized access to protected API routes.

## Phase 5 — UPI Fraud Detection
*   **Prerequisites**: Phase 4, Dataset selection.
*   **Outputs**: Java ML inference service, Fraud API endpoints, Frontend Fraud form and result view.
*   **Dependencies**: Java ML Library.
*   **Verification**: Submitting a test transaction returns a normalized `RiskProfile`.

## Phase 6 — Windows Event Log Analysis (Synchronous/Basic)
*   **Prerequisites**: Phase 4.
*   **Outputs**: EVTX parser integration, basic heuristic rules, file upload API, frontend upload and results view.
*   **Dependencies**: Java EVTX Parser.
*   **Verification**: Uploading a small test EVTX file returns a list of threat indicators.

## Phase 7 — CVE Intelligence
*   **Prerequisites**: Phase 4.
*   **Outputs**: External API client, DB caching logic, CVE API endpoints, frontend CVE search and result view.
*   **Dependencies**: External CVE API (e.g., NVD).
*   **Verification**: Querying a known CVE returns formatted data and caches it in the database.

## Phase 8 — AI Cybersecurity Assistant & Explanations
*   **Prerequisites**: Phases 5, 6, 7.
*   **Outputs**: Spring AI integration, Chatbot UI, prompt templates for Fraud/EVTX/CVE explanations.
*   **Dependencies**: LLM Provider API Key.
*   **Verification**: The assistant responds to general queries; Fraud/CVE results now include AI-generated text.

## Phase 9 — RAG Knowledge Base
*   **Prerequisites**: Phase 8.
*   **Outputs**: pgvector enabled, chunking/embedding pipeline, context-augmented prompt logic.
*   **Dependencies**: Embedding Provider API Key.
*   **Verification**: Assistant correctly answers questions based *only* on ingested test documents.

## Phase 10 — Context-Aware Intelligence
*   **Prerequisites**: Phase 9.
*   **Outputs**: Deep integration allowing the Assistant to query user history (e.g., "Summarize my recent fraud scans").

## Phase 11 — Redis Optimization
*   **Prerequisites**: Phase 4.
*   **Outputs**: Implementation of Redis caching for CVEs and rate limiting.

## Phase 12 — RabbitMQ Background Processing
*   **Prerequisites**: Phase 6.
*   **Outputs**: RabbitMQ instance, asynchronous offloading of EVTX parsing, job status tracking via Redis.
*   **Dependencies**: RabbitMQ.
*   **Verification**: Uploading a massive EVTX file does not block the HTTP thread; frontend polls for completion.

## Phase 13 — Dashboard Intelligence
*   **Prerequisites**: Phases 5, 6, 7.
*   **Outputs**: Dashboard API aggregating metrics across modules, frontend charts (Recharts) implemented.

## Phase 14 — Security Reports
*   **Prerequisites**: Phase 12 (RabbitMQ).
*   **Outputs**: PDF generation library, async report generation worker, download API.
*   **Dependencies**: Java PDF Library.
*   **Verification**: Requesting a report yields a formatted PDF after a short delay.

## Phase 15 — Analysis History
*   **Prerequisites**: All analysis modules.
*   **Outputs**: Unified History API, frontend paginated table view.

## Phase 16 — Testing
*   **Prerequisites**: All previous phases.
*   **Outputs**: High unit test coverage (JUnit/Mockito), critical integration tests.

## Phase 17 — Security Hardening
*   **Outputs**: Final review of CORS, CSRF, input validation, dependency vulnerability scanning.

## Phase 18 — Docker
*   **Outputs**: `Dockerfile` for backend, `Dockerfile` for frontend, `docker-compose.yml` for local environment (Redis, RabbitMQ).

## Phase 19 — Nginx (Reverse Proxy)
*   **Outputs**: Nginx configuration for routing `/api` to backend and serving frontend static files.

## Phase 20 — Monitoring
*   **Outputs**: Spring Boot Actuator configured, Prometheus/Grafana setup (optional based on deployment capacity).

## Phase 21 — Cloudflare (Edge Security)
*   **Outputs**: DNS routing, WAF rules, DDoS protection enabled for the production domain.

## Phase 22 — CI/CD
*   **Outputs**: GitHub Actions workflows for automated testing and building Docker images.

## Phase 23 — Final Integration
*   **Outputs**: Full system deployment and end-to-end verification against the requirements matrix.
