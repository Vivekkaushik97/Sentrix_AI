# Analyst Workspace

## Objective
Provide a unified, single-pane-of-glass workspace for security analysts. It aggregates data across posture, incidents, investigations, and actions into a data-driven UI.

## Backend Architecture
- **Composition over Duplication**: The `AnalystWorkspaceService` orchestrates calls across existing repositories/services (ActionRepository, PostureService, etc.) to aggregate the unified state.
- **Data Boundaries**: Respects existing state logic. Empty collections are returned as `[]` rather than null.
- **DTOs**: Utilizes specific `WorkspaceDto` models to shape the response for the frontend without leaking raw JPA entities.

## Frontend
- **Route**: `/security-operations`
- **Design**: Professional SOC styling using existing Sentrix AI components (Lucide icons, Tailwind cards).
- **Sections**: Security Posture, Priority Alerts, Active Incidents, Open Investigations, Pending Actions, Quick Navigation.

## Artificial Intelligence Boundaries
The Analyst Workspace is fully deterministic. AI may explain alerts or context linked from this workspace, but the underlying data populating the workspace must originate explicitly from PostgreSQL.
