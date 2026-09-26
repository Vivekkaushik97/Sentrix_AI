# Phase 14: Data Retention & Privacy

## Overview
Defined explicit retention policies for enterprise security data to ensure compliance without compromising investigative capabilities.

## Architecture
- **Retention Strategy:**
  - Audit Logs (`enterprise_audit_logs`): Kept for a configurable period (default: 365 days).
  - Security Incidents & Investigations: Kept indefinitely as historical security evidence unless explicitly archived or purged by an `ADMIN`.
  - Ephemeral Events (e.g., raw RabbitMQ payloads): Expired based on queue configurations or limited database partitions if implemented.
- **Rules:**
  - The system will NOT automatically delete security evidence (Incidents, Actions, Investigations) without an explicit, documented retention policy enforced via administrative configuration.
  - Any data deletion acts as a hard purge but triggers a final `enterprise_audit_logs` event recording the purge.
