# Phase 8: Real-Time Dashboard

## Overview
The real-time dashboard is intended to display:
- Security Overview (Active Incidents, Recent Threats, UPI Risk).
- Real-time Activity (Live SSE events).
- Risk Distribution charts.

## Frontend UI Architecture
- The existing `/` route (`Dashboard.tsx`) is designed to consume real-time statistics.
- **Data Integrity**: If the `fetch` endpoints return 0 items, the UI explicitly renders an `EmptyState` explaining "No security activity yet". It does not hallucinate fake "23 threats" or "5 active incidents".

*(Frontend component integration marked as DEFERRED / PENDING further React iteration)*
