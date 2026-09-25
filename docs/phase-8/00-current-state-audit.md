# Phase 8: Current State Audit

## Architecture Overview
The current Sentrix AI platform acts as a consolidated defensive cybersecurity analytics platform.
- **Backend Framework**: Java 25, Spring Boot 3.5.4, Maven.
- **Data Persistence**: Supabase PostgreSQL (via Spring Data JPA, schema managed by Flyway).
- **Messaging & Caching**: RabbitMQ and Redis are configured in `pom.xml` and `application.yml` but have been utilized purely in basic configurations (e.g. Spring Session Redis) without leveraging full asynchronous event pipelines.
- **Frontend**: React, TypeScript, Vite, Tailwind CSS, shadcn/ui. 

## Existing Modules
1. **Fraud Analysis** (`com.sentrix.ai.fraud`): Basic rules engine for transactions. Currently synchronous processing.
2. **Windows Event Intelligence** (`com.sentrix.ai.windowsevent`): Ingestion, normalization, rules, and scoring for Windows EVTX data. Synchronous.
3. **Generic Event Logs** (`com.sentrix.ai.eventlog`): Broad structure for parsing generic logs.
4. **CVE Intelligence** (`com.sentrix.ai.cve`): Tracks vulnerability reports.
5. **AI Assistant / RAG** (`com.sentrix.ai.rag`, `com.sentrix.ai.ai`): A conversational overlay interpreting `Analysis` entities using deterministic facts from the database without hallucinations.
6. **Reporting / History** (`com.sentrix.ai.report`, `com.sentrix.ai.history`): Analytics retrieval.

## What Phase 8 Will Add
1. **Real-time Event Architecture**: Creating core abstractions for generalized `SecurityEvent` ingestion and processing.
2. **RabbitMQ Pipeline**: Shifting processing for UPI transactions and generic security events from synchronous HTTP requests to asynchronous RabbitMQ consumers.
3. **UPI Fraud Operations**: Building a dedicated, normalized `UpiTransaction` model, with a specific deterministic fraud engine and risk scorer.
4. **Security Correlation Engine**: Detecting incidents dynamically across varied inputs (e.g., Windows Events + UPI activity).
5. **Live Updates**: Emitting Server-Sent Events (SSE) or using a suitable mechanism for live frontend dashboard updates.
6. **Incident Investigation UI**: Real-time SOC dashboard expansions, including an `/incidents` view and a `/upi-security` view.

## What Phase 8 Will NOT Modify
- It will NOT replace the Phase 1–7 foundational models.
- It will NOT introduce unauthorized offensive capabilities (e.g., automated execution).
- It will NOT replace the Java/Spring Boot framework.
- It will NOT abandon the Supabase PostgreSQL source of truth.
- It will NOT change the core Sentrix aesthetic design system.
