# Phase 15: Notification Governance

## Overview
Refined the Phase 14 Notification architecture with explicit governance and routing preferences.

## Mechanisms
- **Event Routing:** Only permitted event classes (`CRITICAL_INCIDENT`, `ACTION_APPROVAL_REQUIRED`, `COMPLIANCE_FAILURE`) trigger the notification bus.
- **No Uncontrolled Delivery:** Sentrix remains isolated. The provider abstraction logs events locally. If an external bridge (e.g., Slack) is wired in, it relies entirely on safe, audited enterprise configurations without bypassing `SecurityConfig` boundaries.
