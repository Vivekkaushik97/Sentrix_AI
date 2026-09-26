# Phase 14: Notification Architecture

## Overview
Designed an enterprise-safe notification abstraction for alerting analysts to operational shifts.

## Architecture
- **Abstraction:** Introduced the `NotificationService` interface and `NotificationProvider` interface. 
- **Delivery Mechanisms:** By default, the system operates with a `NoOpNotificationProvider` or a `LoggingNotificationProvider` to ensure no fake emails or SMS messages are accidentally sent to unverified addresses. 
- **Supported Events:**
  - Critical severity incidents created.
  - An analyst is assigned to an investigation.
  - A security action moves to `PENDING_APPROVAL` (alerting `SECURITY_MANAGER`s).
  - A security action execution completes or fails.

## Configuration & Safety
- Real notifications (e.g., SMTP, Slack Webhooks, PagerDuty) require explicit application properties configured via environment variables.
- Delivery logic includes circuit breakers and fallback mechanisms to ensure a failure in the notification provider does not cause the core security event to fail (e.g., an incident will still be created even if the Slack alert fails).
