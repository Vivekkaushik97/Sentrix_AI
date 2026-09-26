# Phase 14: Frontend Enterprise UX

## Overview
Upgraded the React/Vite UI to reflect the enterprise operations and RBAC logic, ensuring users only see what they can interact with.

## Enhancements
- **Role-Aware Navigation:** Tabs for Administration and Action Approvals are hidden from `VIEWER` and standard `SECURITY_ANALYST` users.
- **Identity Display:** The current authenticated user and their assigned roles are displayed prominently.
- **Ownership Controls:** Incidents and Investigations now feature assignment dropdowns (comboboxes) fetching real `SecurityUser` lists from the backend.
- **Action Center:** Action cards now display the proposer's identity, the time proposed, and the assigned approver.

## Crucial Rule Enforced
Frontend permission checks (e.g., hiding the "Execute Action" button) are strictly a UX layer to prevent confusion. The authoritative authorization boundary rests purely in the backend controllers and services.
