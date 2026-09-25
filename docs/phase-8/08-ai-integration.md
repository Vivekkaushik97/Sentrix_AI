# Phase 8: AI Integration

## Overview
The Phase 5 AI Assistant was built on the `SecurityContextBuilder`, which queries global `Analysis` records.

## Extension
For Phase 8, `SecurityIncident` and `UpiTransaction` details are conceptually integrated into the context payload without rewriting the AI prompt. The prompt retains its strict instruction to *only answer based on the facts provided*. It explains why a UPI transaction scored 75 (velocity burst) but does not secretly assign the score itself.
