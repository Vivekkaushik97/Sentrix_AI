# Phase 16: Threat Intelligence Providers

## Overview
Created a provider-neutral interface `ThreatIntelligenceProvider` to abstract external API calls.

## Design
- `DefaultNoOpThreatIntelligenceProvider`: Operates safely out-of-the-box, returning empty but valid response structures. Prevents crashes when external providers are not configured.
- **Provider Constraints:** Does not require external credentials by default. Does not fabricate threat intel responses. Future plugins can implement the interface without rewriting the core Intelligence Engine.
