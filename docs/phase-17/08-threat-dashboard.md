# Phase 17: Threat Intelligence Dashboard

## Overview
Structured the operational SOC dashboard to visualize live intelligence.

## Visualization Constraints
- The UI populates dynamically by computing roll-ups from `threat_indicators` and `threat_campaigns`.
- If no IOCs exist, the platform renders a valid empty state instead of rendering hallucinated percentages or mock statistics.
