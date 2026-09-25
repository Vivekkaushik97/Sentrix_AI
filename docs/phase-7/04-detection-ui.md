# Phase 7: Detection UI

## Overview
Visualizes specific security findings triggered by the `WindowsRuleEngine`.

## Data Rendered
- `Rule ID` (e.g., `WIN-AUTH-4625`)
- `Severity` (Color-coded badge: CRITICAL, HIGH, MEDIUM, LOW)
- `Reason` (Human-readable explanation from the backend)
- `Evidence` (Raw snippet justifying the finding, rendered in a mono-spaced code block)

## Policy
The UI never calculates severity or detections on the client side; it acts exclusively as a dumb terminal rendering the backend's deterministic security logic.
