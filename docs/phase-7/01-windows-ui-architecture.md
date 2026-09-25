# Phase 7: Windows UI Architecture

## Overview
The Windows Security Intelligence interface extends the existing Sentrix AI Phase 2 Dashboard shell to visualize real Windows Event Log data processed by Phase 6.

## Component Design
- **Container**: `src/pages/WindowsEvents.tsx`
- **Routing**: Mounted at `/windows-events` inside the `DashboardLayout`.
- **Navigation**: Accessible via the `Sidebar` under "Windows Security".
- **Tabs**: Custom UI implementation dividing the workflow into Timeline, Detections, Correlations, and Ingestion.

## Philosophy
The UI strictly adheres to the "No Fake Data" policy. State is driven purely by the backend REST API responses. Empty states are rendered beautifully when zero events or detections exist.
