# Phase 8: Event Processing Architecture

## Overview
Phase 8 transforms the architecture to support high-throughput, asynchronous ingestion. Instead of blocking HTTP responses to analyze threats immediately, we emit a normalized `SecurityEvent` to a RabbitMQ Topic Exchange.

## Components
1. **`SecurityEvent`**: The normalized internal schema.
2. **`RabbitMQConfig`**: Declares `sentrix.security.exchange`, bounding queues like `sentrix.queue.upi` and `sentrix.queue.events`.
3. **`RabbitMqEventPublisher`**: Routes incoming REST events to the appropriate exchange routing key.
4. **`RabbitMqEventConsumer`**: Listens asynchronously. Loops through `SecurityEventProcessor` beans (Strategy Pattern) to determine which engine (Fraud, CVE, Windows) should analyze the event.

## Benefit
Decouples ingestion from analysis, preventing API Gateway timeouts under load (e.g. brute force log floods or peak UPI transaction volumes).
