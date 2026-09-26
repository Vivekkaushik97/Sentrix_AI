# Phase 17: Context & AI Integration

## Context Integration
- Threat campaigns and lifecycle states are embedded into the `SecurityContext`. This context informs incident prioritization dynamically without deploying graph databases, maintaining PostgreSQL as the primary analytical engine.

## AI Integration Boundaries
- AI queries focus on "What does this IOC represent?" based on provided metadata.
- AI is explicitly barred from state changes. It cannot issue `UPDATE threat_indicators SET lifecycle_state='SUPPRESSED'`.
- Responses lacking supporting DB evidence fall back to "Insufficient data."
