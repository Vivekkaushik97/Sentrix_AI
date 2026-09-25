# Phase 10: Security Context Model

## Abstraction
We are introducing the `SecurityContextDto` (no JPA entity needed since we do not duplicate database records).

## Model 
- **SecurityContextDto**
  - `source`: The entity being analyzed (e.g., "WINDOWS_EVENT")
  - `entityId`: Its ID
  - `riskScore`: Reused from existing deterministic engines
  - `severity`: Standard enum
  - `graph`: A visual relational graph representation (Nodes + Edges)
  - `timeline`: Aggregation of related timeline events
  - `provenance`: Array of tables/sources where data was fetched.
