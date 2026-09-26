# Phase 16: AI Integration

## Overview
Expanded the AI Assistant to intelligently reason over the newly integrated Threat Intelligence data.

## Capabilities
- AI can summarize observations associated with a specific indicator.
- AI can explain the relationships established via `threat_indicator_correlations`.

## Strict Boundaries
- **No Reputation Fabrication:** AI MUST NEVER invent IOC reputation. If the backend score is 0, the AI cannot claim the IP is a known malicious actor.
- **No Context Hallucination:** The AI only answers based on the deterministic context graph provided to it via the backend context service.
- **No Action Execution:** The AI remains completely walled off from executing or approving actions based on threat intel.
