# Phase 0: Frontend Architecture & Routing

This document defines the frontend routing structure for the React + Vite application.

## Frontend Technology Stack
* React
* Vite
* TypeScript
* React Router (Routing)
* Tailwind CSS + shadcn/ui (Styling & Components)
* Framer Motion (Animations)
* Recharts (Data Visualization)
* Axios (HTTP Client)

## Route Structure

| Route | Purpose | Component | Data Requirements (API) |
|---|---|---|---|
| `/` | Landing / Welcome page. Initializes session. | `LandingPage` | `POST /session/init` |
| `/dashboard` | High-level system overview. | `DashboardPage` | `GET /dashboard/metrics` |
| `/fraud` | Hub for fraud detection. Lists past fraud checks. | `FraudHubPage` | `GET /analyses?type=FRAUD` |
| `/fraud/new` | Form to submit a new UPI transaction for analysis. | `NewFraudAnalysisPage`| `POST /fraud/analyze` |
| `/fraud/:id` | Detailed view of a specific fraud analysis & AI explanation. | `FraudResultPage` | `GET /fraud/{id}` |
| `/event-logs` | Hub for EVTX analysis. Lists past uploads. | `EventLogHubPage` | `GET /analyses?type=EVENT_LOG` |
| `/event-logs/new` | File upload interface for EVTX files. | `UploadEventLogPage` | `POST /event-logs/upload` |
| `/event-logs/:id` | Parsed threat report and log viewer. | `EventLogResultPage` | `GET /event-logs/{id}` |
| `/cves` | Form to lookup CVEs and list past lookups. | `CveLookupPage` | `POST /cves/lookup`, `GET /analyses?type=CVE` |
| `/cves/:id` | Detailed view of a CVE and its AI explanation. | `CveResultPage` | `GET /cves/{id}` |
| `/assistant` | AI Chatbot interface. | `ChatAssistantPage` | `POST /chat/...` |
| `/history` | Global history of all analyses across all modules. | `GlobalHistoryPage` | `GET /analyses` |
| `/reports` | Hub for generated reports and requesting new ones. | `ReportsPage` | `GET /reports` |
| `*` | 404 Not Found | `NotFoundPage` | None |

## State Management Concept

* **Global State**: React Context or lightweight store (Zustand) for Theme (Dark/Light) and Session Status.
* **Server State**: React Query (or direct Axios + `useEffect` if keeping it simple) for API data fetching, caching, and loading states.
* **Form State**: React Hook Form with Zod validation.

## Standard UI States for Every Route

Every data-fetching component must handle four states:
1. **Idle**: Initial state before action.
2. **Loading**: Skeleton loaders (shadcn `Skeleton`) indicating data is being fetched. Avoid jarring spinners.
3. **Error**: User-friendly error message component with retry functionality.
4. **Success/Empty**: The actual data visualization, or a stylized "No Data Found" empty state illustration.

## Navigation Behavior
* Sidebar navigation for main modules (Dashboard, Fraud, Event Logs, CVEs, Assistant, History).
* Breadcrumbs for deep links (e.g., `Home > Event Logs > Analysis Result`).
* Protected Routes mechanism: Even though sessions are anonymous, the app should ensure a session cookie exists before allowing access to `/dashboard` or beyond. If missing, redirect to `/` or call initialization transparently.
