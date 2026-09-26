# Phase 15: Current State Audit

## Existing Architecture Overview
- **Core Platform:** Spring Boot backend and React/Vite frontend. PostgreSQL is the central, authoritative source of truth. Redis acts as a cache, and RabbitMQ serves as an asynchronous event bus.
- **Enterprise Security (Phase 14):** Introduced a strong RBAC authorization model (ADMIN, SECURITY_MANAGER, SECURITY_ANALYST, VIEWER), mapping entities to `security_users` (V10). Incidents and investigations support ownership and collaborative assignments (V11). A centralized immutable audit trail was established in `enterprise_audit_logs` (V12).
- **Security Posture & Action Execution:** The `ApprovalGate` restricts security actions from autonomous execution by AI. Actions require explicit approval by authorized personnel.

## Missing Capabilities for Phase 15
- **Compliance & Evidence:** There is no framework-agnostic compliance assessment structure (e.g., SOC2, ISO27001). Evidence collection and mapping to controls is completely missing.
- **Security Governance Policies:** Configurable policies (retention, action approval policies) are undocumented in the database schema.
- **Enterprise Configuration UI:** Missing a centralized configuration surface for notification preferences and retention management.

## Risks & Conflicts
- Introducing compliance assessments could conflict with the "no fake data" rule if not strictly tied to live, deterministic evidence collected within the platform.
- AI must be securely restricted from auto-evaluating compliance checks as "passed" without verifiable underlying evidence.
- Overengineering compliance reporting might break existing UI layout conventions.

## Implementation Strategy
- Use Migration `V13__enterprise_compliance.sql` to model compliance controls, requirements, evidence linking, and platform policies securely without modifying V1–V12.
- Adhere strictly to existing REST API paradigms. Add new controllers for `/api/compliance` and `/api/evidence`.
