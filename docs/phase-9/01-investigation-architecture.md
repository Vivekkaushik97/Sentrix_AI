# Phase 9: Investigation Architecture

## Unified Abstraction
The `Investigation` model serves as the top-level analytical workspace for human analysts. It is distinct from `SecurityIncident` which is a system-generated correlation.

## Data Model
- **Investigation**: Has a `title`, `description`, `status` (OPEN/CLOSED), `priority`, and `owner`.
- **InvestigationEvent**: Represents the timeline. Contains `eventType` (e.g. UPI, WINDOWS, INCIDENT), `referenceId` (UUID of the external system), and a `summary`.

## APIs
Implemented `POST /api/v1/investigations` to spawn investigations and `POST /api/v1/investigations/{id}/events` to link disparate system elements (Windows, UPI) into the same timeline via standard HTTP semantics.
