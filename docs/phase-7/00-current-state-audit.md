# Phase 7: Current State Audit

## Overview
Auditing the repository state at the beginning of Phase 7 to build the Windows Security Intelligence UI.

## Backend State
- `WindowsEventController.java` currently only exposes a `POST /api/v1/windows-events/ingest` endpoint.
- There are no `GET` endpoints exposed for the frontend to retrieve ingested events, detections, or correlations.
- The `WindowsEventRepository` has `findByComputerName` and `findByTimestampBetween`, but no exposed controller methods.
- **Action**: I MUST modify `WindowsEventController` and the corresponding service to expose `GET` endpoints for events, analyses, detections, and correlations to power the frontend, as explicitly permitted by the rules ("Only modify backend code when absolutely necessary to expose an already-existing Phase 6 capability").

## Frontend State
- Routing is managed in `App.tsx` and the sidebar via standard React routes.
- The UI uses Tailwind CSS, shadcn/ui components, and Lucide icons.
- No `WindowsEvents` page currently exists.
- The design system dictates light-theme SOC aesthetics without "fake terminal" gaming aesthetics.
- **Action**: Create `src/pages/WindowsEvents.tsx`, `src/api/windowsEvents.ts`, and `src/types/windowsEvents.ts`. Add route to `App.tsx` and Sidebar.

## AI / RAG Architecture
- `SecurityContextBuilder` currently pulls `WindowsEventAnalysis`.
- `AIAssistant.tsx` works for general queries. We can provide context-aware prompt pre-fills in the Windows Event UI to seamlessly integrate the assistant.

## Conclusion
The exact API contracts for data retrieval must be defined in the backend first to provide real data to the frontend timeline, detection panel, and empty states.
