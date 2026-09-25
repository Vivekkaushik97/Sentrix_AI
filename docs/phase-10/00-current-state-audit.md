# Phase 10: Current State Audit

## Overview
Sentrix AI currently exists as a set of highly normalized, functional security domains implemented over Phases 1–9.

## Repositories & Entities
- **UPI Security**: `UpiTransaction`, `FraudAnalysis`. Handled by `UpiSecurityService`.
- **Windows Security**: `WindowsEvent`, `WindowsEventCorrelation`. Handled by `WindowsEventIngestionService`.
- **Security Incidents**: `SecurityIncident`, `IncidentEvent`. Groups related events deterministically via `IncidentEngine`.
- **Investigations**: `Investigation`, `InvestigationEvent`, `InvestigationCorrelation`. A human analyst workspace that references raw external data via IDs.

## Existing AI Architecture
- `AiAssistantService` uses an `AiProvider` abstraction (Mock or external API) to generate text.
- `SecurityContextBuilder` generates raw string context out of entity pieces for the AI prompt.

## Gap for Phase 10
Currently, Sentrix AI stores the connections (e.g. `IncidentEvent` maps an incident to a raw `entityReferenceId`, and `InvestigationCorrelation` maps source to target). However, there is no generic unified "Graph Context" abstraction that takes a single event (e.g., a Windows Event) and walks these relational edges to build a holistic `SecurityContext` containing nodes, edges, and a combined timeline.

## Infrastructure
- PostgreSQL / Flyway
- RabbitMQ / SSE (`/api/v1/live/events`)
- Redis

We will build the Context resolution on top of these without fabricating data or replacing the DB.
