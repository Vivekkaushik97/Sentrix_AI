# Phase 15: Compliance Control Model

## Overview
Established a deterministic, framework-agnostic schema to manage enterprise compliance controls (e.g., SOC2, ISO27001).

## Architecture
- **Schema (`V13__enterprise_compliance.sql`):** 
  - `compliance_frameworks`: Represents a standard (e.g., "SOC2 Type 2", "ISO27001:2022").
  - `compliance_controls`: Represents specific requirements (e.g., "CC6.1 Logical Access").
- **Constraints:**
  - Sentrix AI does not pretend to hold a certification. It simply models the frameworks for internal tracking.
  - Controls are data-driven structures. 

## Services
- `ComplianceService` abstracts the fetching and modeling of these frameworks via REST APIs (`/api/compliance/frameworks`).
