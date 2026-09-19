# Frontend Routing Architecture

**Framework**: React (Vite) + React Router

## Route Structure

| Route | Purpose | Expected Data/State | Permission/Session |
|---|---|---|---|
| `/` | Landing page / Intro | Static marketing info | Public |
| `/dashboard` | System overview | Summary metrics, recent analyses list | Requires Session |
| `/fraud` | Fraud detection entry | Form for transaction details | Requires Session |
| `/fraud/:id` | Fraud analysis result | Fraud probability, AI explanation, Risk Score | Requires Session |
| `/event-logs` | Event log upload | File upload component | Requires Session |
| `/event-logs/:id` | Event log analysis result | Parsed threats, overall risk, AI summary | Requires Session |
| `/cves` | CVE search interface | Search input | Requires Session |
| `/cves/:id` | Specific CVE details | CVSS, descriptions, AI context | Requires Session |
| `/assistant` | AI Chat interface | Chat history, message input | Requires Session |
| `/history` | Combined analysis history | Paginated list of past analyses | Requires Session |
| `/reports` | Generated reports list | List of PDFs/HTMLs | Requires Session |
| `/reports/:id` | Report view | Report document viewer | Requires Session |

## Behavior
* **Loading State**: Skeletons for dashboard elements; loaders/spinners for async actions.
* **Error State**: Global Error Boundary for crashes; localized error alerts (shadcn/ui toasts) for API failures.
* **Empty State**: Custom graphics and text prompting the user to run their first analysis.
* **Session Handling**: If a session cookie expires or is missing on a protected route, the app should automatically call `/api/v1/session/init` in the background and retry, providing a seamless anonymous experience.
