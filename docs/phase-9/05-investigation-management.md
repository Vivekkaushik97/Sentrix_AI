# Phase 9: Investigation Management

## Implementation Details
- **Status Workflows**: Handled via `InvestigationUpdateDto`. Supported status: `OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED`.
- **API Surface**: Exposed `PATCH /api/v1/investigations/{id}` for metadata updates (Status/Priority). 
- **Auto-Completion**: Setting status to `CLOSED` or `RESOLVED` automatically stamps `closedAt` to maintain timeline integrity.
- **Client**: `updateInvestigation` added to `src/api/investigations.ts`.
