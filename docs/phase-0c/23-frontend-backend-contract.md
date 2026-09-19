# Frontend-Backend Contract Matrix

| Frontend Route | Required API Calls | Action |
| --- | --- | --- |
| `/` (Init) | `POST /api/v1/session` | Start session if missing |
| `/dashboard` | `GET /api/v1/dashboard/summary` | Load stats |
| `/history` | `GET /api/v1/analyses?page=0...` | List history |
| `/fraud/new` | `POST /api/v1/fraud` | Submit inference |
| `/fraud/:id` | `GET /api/v1/fraud/{id}` | View result |
| `/event-logs/new` | `POST /api/v1/event-logs/upload` | Upload EVTX |
| `/event-logs/:id` | `GET /api/v1/event-logs/{id}` | Poll/View result |
| `/cves` | `GET /api/v1/cves/search?q=...` | Look up CVE |
| `/cves/:id` | `POST /api/v1/cves/{id}/analyze` | Get AI context |
| `/assistant` | `POST` & `GET` on `/api/v1/chat/...` | Conversational UI |
| `/reports` | `POST` & `GET` on `/api/v1/reports` | Request/List PDFs |
