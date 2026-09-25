# Phase 6: Detection Rules

## Overview
The rule engine identifies malicious or anomalous behavior in Windows Event Logs. Rules implement the `WindowsEventRule` interface, ensuring modularity.

## Rules Implemented
1. **`WIN-AUTH-4625` (FailedAuthRule)**
   - **Trigger**: 5 or more Event ID 4625 (Failed Logon) for the same user within 5 minutes.
   - **Severity**: HIGH
   - **Risk Contribution**: +30
   - **Reason**: Identifies potential brute-force or password spraying attacks.

2. **`WIN-AUDIT-CLEAR` (AuditLogClearRule)**
   - **Trigger**: Event ID 104 (System log cleared) or 1102 (Security log cleared).
   - **Severity**: CRITICAL
   - **Risk Contribution**: +50
   - **Reason**: A highly reliable indicator of defense evasion.

## Architecture
By filtering execution through `appliesTo(WindowsEvent event)`, the engine ensures fast evaluation across large batches. State-dependent rules (like brute force) utilize historical context passed in from the persistence layer. No ML hallucinations are used; detections are 100% deterministic and explainable.
