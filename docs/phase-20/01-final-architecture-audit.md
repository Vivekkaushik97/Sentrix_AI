# Phase 20: Complete Architecture Audit

## System Map

```
Frontend (React/Vite)
    ↓ (REST / SSE)
Controllers (API Boundaries)
    ↓
Spring Security (RBAC / JWT Interceptors)
    ↓
Application Services (Operations Orchestration, Risk Aggregation)
    ↓
Domain Services (ApprovalGate, AI Prompts, Threat Engines)
    ↓
PostgreSQL (V1-V17)
    ↓ (Async Events)
RabbitMQ (Logging, Alerting) / Redis (Cache)
```

## Security Pipeline Isolation
- **Threat Intelligence & Hunting:** Ingests IOCs and issues Hunts cleanly into internal mappings (`risk_aggregations`, `threat_hunt_findings`).
- **Context to Investigation:** Contextual events map deterministically to high-level incidents.
- **Incident to Risk:** Risk is calculated (0-100) via strict database indices.
- **Action Proposing:** Automatically proposed actions land safely in `PENDING_APPROVAL`.
- **Action Execution:** Only human triggers traverse the `ApprovalGate` to the `Controlled Executor`.
