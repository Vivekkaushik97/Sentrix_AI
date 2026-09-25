# Phase 8: Live Events (SSE)

## Overview
A real-time dashboard is implemented utilizing Server-Sent Events (SSE). 

## Architecture
- `LiveEventService` acts as a `SecurityEventProcessor`. Since it sits within the global processing chain invoked by the `RabbitMqEventConsumer`, any payload arriving on the message broker is implicitly piped into the active SSE streams.
- Connected browsers maintain a long-lived GET request to `/api/v1/live/events`.
- Emitted JSON payloads match the `SecurityEvent` schema, allowing the React frontend to display live notifications and ticker updates without manual polling.
