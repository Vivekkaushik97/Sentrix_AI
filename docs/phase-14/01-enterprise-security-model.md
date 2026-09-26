# Phase 14: Enterprise Security Model

## Roles and Responsibilities

The authorization model is built around four primary roles, ensuring the principle of least privilege:

1. **ADMIN**
   - Full system access.
   - Can manage users, roles, and enterprise configuration.
   - Can bypass certain restrictions (e.g., reassigning stuck investigations).
   - Can approve and execute security actions.

2. **SECURITY_MANAGER**
   - Can oversee incidents, investigations, and posture reporting.
   - Can assign/reassign analysts to incidents.
   - Can approve security actions proposed by analysts.
   - Cannot manage system-level configuration or users.

3. **SECURITY_ANALYST**
   - Can view and manage their assigned incidents and investigations.
   - Can propose security actions (which may require manager approval depending on policy).
   - Can add notes and evidence to investigations.
   - Can execute pre-approved or low-risk actions if policy permits.

4. **VIEWER**
   - Read-only access to dashboards, reports, and alerts.
   - **CRITICAL RESTRICTION:** Cannot propose, approve, or execute any security actions.
   - Cannot modify incidents, investigations, or configurations.

## Core Security Principles

- **AI Boundary:** The AI acts as an advisor. It does not have an identity that allows it to bypass authorization. It cannot approve or execute actions, regardless of its confidence level.
- **Action Approval:** Security actions require human approval. Approval checks ensure the actor holds the necessary role (`SECURITY_MANAGER` or `ADMIN`) and that the action hasn't expired.
- **Resource Authorization:** Access to specific incidents/investigations can be restricted based on assignment, ensuring strict operational boundaries.
