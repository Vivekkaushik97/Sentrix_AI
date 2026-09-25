# Sentrix AI

## Overview
Sentrix AI is a comprehensive, Generative-AI-based cybersecurity intelligence and Security Operations Center (SOC) platform designed to assist human security analysts. It provides real-time security analytics, fraud intelligence, Windows security intelligence, event-log analysis, CVE intelligence, UPI fraud intelligence, and security incident correlation. Sentrix AI unifies human-led investigations, context-aware security intelligence, controlled security action orchestration, security posture analysis, and alert prioritization into a single-pane-of-glass Analyst Operations Workspace with an advisory AI assistant.

AI remains strictly advisory and human-controlled. Sentrix AI is NOT a fully autonomous security agent.

## Current Status

- **Phases 1–12 implemented.**
- **Phase 12 finalized.**
- Backend and frontend builds verified according to the latest validation.
- Human-in-the-loop security action architecture is active.
- AI remains advisory.
- No Phase 13 functionality has been implemented.

**Deferred Functionality (Phase 12):**
- Analyst Workspace correlated Incident/Investigation references remain deferred/conditionally empty until deeper repository composition is implemented.

## Key Capabilities

1. **Security Analytics**
   - Fraud analysis
   - Event log analysis
   - CVE intelligence
   - Security risk analysis

2. **Windows Security Intelligence**
   - Windows event ingestion
   - Normalization
   - Deterministic detection rules
   - Failed authentication detection
   - Audit log clearing detection
   - Correlation
   - Risk scoring
   - Windows security UI

3. **Real-Time Security Intelligence**
   - RabbitMQ event processing architecture
   - Security event publishing/processing
   - Incident correlation
   - SSE live events
   - UPI security processing

4. **UPI Fraud Intelligence**
   - UPI transaction ingestion
   - Fraud rules
   - Velocity analysis
   - Deterministic risk scoring

5. **Security Incidents**
   - Incident creation/correlation
   - Incident investigation
   - Incident detail views
   - Live event integration

6. **Investigation Workspace**
   - Investigation lifecycle
   - Investigation events
   - Cross-source correlations
   - Investigation timeline
   - Evidence references
   - AI-assisted investigation summaries

7. **Context-Aware Security Intelligence**
   - Security context resolution
   - Graph-style nodes and edges
   - Deterministic relationships
   - Context visualization
   - AI context explanation

8. **Controlled Security Actions**
   - Action proposals
   - Approval workflow
   - Approval gate
   - Controlled action lifecycle
   - Audit trail
   - Dry-run execution
   - No arbitrary command execution

9. **Phase 12 Security Operations**
   - Security posture engine
   - Posture snapshots
   - Alert prioritization
   - Unified security search
   - Advanced security dashboard
   - Analyst workspace
   - Observability/correlation IDs

## Phase Roadmap

| Phase | Area | Status |
|------|------|--------|
| 1 | Core foundation | Complete |
| 2 | Frontend/UI foundation | Complete |
| 3 | Security analysis foundation | Complete |
| 4 | Event/CVE intelligence | Complete |
| 5 | AI/RAG foundation | Complete |
| 6 | Windows event intelligence | Complete |
| 7 | Windows security UI | Complete |
| 8 | Real-time security & UPI fraud | Complete |
| 9 | Investigation workspace | Complete |
| 10 | Context-aware security intelligence | Complete |
| 11 | Controlled security actions | Complete |
| 12 | Security operations & production readiness | Complete |

Current project status: Phase 12 COMPLETE.

## Architecture

**Frontend:**
- React, TypeScript, Vite
- Tailwind/shadcn-style components
- API abstraction layer
- SOC dashboards and operational pages

**Backend:**
- Java, Spring Boot, REST APIs
- JPA/Hibernate, Service/repository architecture, DTO boundaries

**Persistence:**
- PostgreSQL / Supabase, Flyway migrations

**Infrastructure:**
- Redis, RabbitMQ, Server-Sent Events, External NVD/CVE provider

**AI:**
- Provider abstraction, Context-aware AI, RAG abstractions, AI safety boundaries, Advisory-only operation

**Observability:**
- Correlation IDs, Structured error handling, Security audit logging

## AI Safety & Human-in-the-Loop

- AI is advisory.
- AI does not independently authorize security actions.
- AI does not execute arbitrary shell commands.
- AI does not execute arbitrary URLs.
- AI does not directly modify database state.
- Security actions use explicit allowlisted ActionType values.
- Actions pass through an ApprovalGate.
- Execution is controlled through registered executors.
- DryRunExecutor is used for safe simulated execution.
- Investigation/context AI uses available evidence rather than inventing relationships.
- Empty/insufficient data should result in insufficient/empty states rather than fabricated security events.

## Security Principles

- DTO boundaries
- Validation
- Centralized error handling
- No stack trace leakage
- Externalized secrets
- Environment-based configuration
- Deterministic detection
- Bounded risk scoring
- Human approval
- Audit logging
- No fake data
- Read-only AI context where applicable
- Controlled action execution
- Correlation IDs
- Safe observability configuration

## AI Architecture

- AI provider abstraction (Default/mock provider behavior)
- Security context injection (Investigation context, Windows security context)
- RAG abstractions (RetrievalService, VectorStore abstraction, EmbeddingProvider abstraction)
- Context-aware explanations

AI is designed as an advisory intelligence layer. AI does not independently authorize or execute security actions.

## Database & Migrations

V1 — initial schema
V2 — Windows Event Intelligence
V3 — UPI Security
V4 — Security Incidents
V5 — Investigations
V6 — Investigation Correlations
V7 — Security Actions
V8 — Security Posture

## API Overview

- `/api/v1/fraud/*`
- `/api/v1/event-logs/*`
- `/api/v1/cves/*`
- `/api/v1/windows-events/*`
- `/api/v1/upi/*`
- `/api/v1/incidents/*`
- `/api/v1/investigations/*`
- `/api/v1/context/*`
- `/api/v1/actions/*`
- `/api/v1/live/*`
- `/api/v1/dashboard/*`
- `/api/v1/analyst/*`
- `/api/v1/search/*`
- `/api/v1/posture/*`

## Project Structure

```text
Sentrix_AI/
├── backend/               # Spring Boot Application (api)
│   ├── .mvn/              # Maven Wrapper
│   ├── src/main/java      # Source code (com.sentrix.ai)
│   └── src/main/resources # Flyway migrations, configuration
├── frontend/              # React/Vite Application
│   ├── src/               # React components, pages, context, and layout
│   ├── package.json       # npm dependencies
│   └── tailwind.config.js # Tailwind CSS configuration
├── docs/                  # Architecture, ADRs, Phase specs
├── docker-compose.yml     # Local services (Redis, RabbitMQ)
├── .env.example           # Environment variables template
└── README.md              # This file
```

## Setup & Installation

### Backend
Requires Java 21.

1. Ensure environment variables are configured (see Configuration).
2. Start local RabbitMQ & Redis via `docker-compose up -d`.
3. Use Maven wrapper to run:
   ```bash
   cd backend
   .\mvnw.cmd clean spring-boot:run
   # (or ./mvnw clean spring-boot:run on Linux/Mac)
   ```

### Frontend
1. Install dependencies:
   ```bash
   cd frontend
   npm install
   ```
2. Build for production:
   ```bash
   npm run build
   ```
3. Run development server:
   ```bash
   npm run dev
   ```

## Configuration

Use `.env` for secrets:
```ini
SPRING_DATASOURCE_URL=jdbc:postgresql://[YOUR_SUPABASE_DB_URL]:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=
SPRING_REDIS_HOST=localhost
SPRING_REDIS_PORT=6379
SPRING_REDIS_PASSWORD=
SPRING_RABBITMQ_HOST=localhost
SPRING_RABBITMQ_PORT=5672
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```

## Testing & Validation

Current Validation Status (Phase 12 finalization):
- Backend: 38/38 tests passed
- Maven verification: PASS
- Frontend build: PASS
- TypeScript compilation: PASS
- Secret scan: PASS
- No Phase 13 functionality implemented
- No autonomous execution introduced

*(Note: Redis and RabbitMQ runtime tests are skipped if local Docker is unavailable.)*

## Documentation

- [Phase 4](docs/phase-4/)
- [Phase 5](docs/phase-5/)
- [Phase 6](docs/phase-6/)
- [Phase 7](docs/phase-7/)
- [Phase 8](docs/phase-8/)
- [Phase 9](docs/phase-9/)
- [Phase 10](docs/phase-10/)
- [Phase 11](docs/phase-11/)
- [Phase 12](docs/phase-12/)

## Security Principles

Sentrix AI is built on a Human-In-The-Loop orchestration architecture. No fake data is used, arbitrary execution is blocked.

## Current Limitations

Search scaling limits to 100 hardcoded items. Enterprise volumes may require pagination.

## Phase 13 Status

Phase 13 (Autonomous Security Agents) — NOT STARTED.
