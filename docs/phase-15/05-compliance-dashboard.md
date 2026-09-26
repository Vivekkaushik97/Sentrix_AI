# Phase 15: Compliance Dashboard

## Overview
Extended the frontend App layout with a dedicated `/compliance` route to visualize assessment posture.

## Implementation Rules
- **Legitimate States:** The dashboard maps data directly from the `control_assessments` and `compliance_controls` tables.
- **No Hallucinated Metrics:** If 0 controls exist, the dashboard shows "No Frameworks Configured". It does not generate fake 95% passing pie charts.
- **Navigation:** Integrated cleanly into the existing Phase 2/12 `Sidebar` component.
