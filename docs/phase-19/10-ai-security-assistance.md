# Phase 19: AI Security Analyst Assistance

## Overview
Integrated AI deeply into the response orchestration tier, while rigorously enforcing safety guardrails.

## Capabilities & Constraints
- **Allowed:** AI can summarize a queue of high-risk items. AI can explain why a specific action (e.g., Block IP) was recommended by the engine, referencing the `calculation_reason` payload from `risk_aggregations`.
- **Prohibited:** AI cannot change a risk score. AI cannot authorize a `PENDING_APPROVAL` action. AI cannot simulate or invent evidence to justify an action.
- **Grounding:** The prompt explicitly instructs the LLM that "You are an advisor. You cannot execute these tasks. You must instruct the user to click the Approve button in the UI."
