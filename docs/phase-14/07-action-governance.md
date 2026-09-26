# Phase 14: Advanced Security Action Governance

## Overview
Hardened the Phase 11 Action Center with strict, enterprise-grade governance controls to ensure robust checks and balances.

## Governance Improvements
1. **Separation of Duties (SoD):**
   - Implemented logic within `ApprovalGate` ensuring that the user who *proposed* a security action cannot be the same user who *approves* it, unless the user holds the `ROLE_ADMIN` authority.
   
2. **Role-Based Approvals:**
   - Security actions can only be approved by users with the `SECURITY_MANAGER` or `ADMIN` role. 
   - `VIEWER` and `SECURITY_ANALYST` (for high-risk actions) are explicitly denied approval rights.

3. **Execution Attribution & Immutable Audit:**
   - The user ID of the proposer, the approver, and the executor are all recorded immutably in the action history.
   - Rejections now require an explicit `rejection_reason`.

4. **Duplicate Prevention & Expiration:**
   - Approvals are idempotent. A second approval attempt on an already `EXECUTING` action is rejected.
   - Proposed actions have a Time-To-Live (TTL); if unapproved within a configurable window, they transition to `EXPIRED`.

## AI Boundary Enforcement
- The system rigidly enforces that AI suggestions map only to `PROPOSED` state. 
- The AI context cannot manipulate the authenticated `SecurityContext`, ensuring it remains impossible for AI to approve its own suggestions.
