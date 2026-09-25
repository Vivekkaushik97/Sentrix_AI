# Phase 7: Event Timeline

## Overview
The Event Timeline renders an audit trail of ingested Windows Event Logs.

## Implementation
- Displays the Event ID, Provider, Timestamp, Computer (Host), User, and Log Name.
- Uses the Shadcn `Card` component for structure.
- Highlights the left border with the primary theme color.
- If no events are fetched, the UI renders the standard `EmptyState` component encouraging the user to "Ingest Events".
