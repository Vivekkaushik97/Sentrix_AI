# Phase 5: AI Assistant API

## Overview
The AI Assistant API (`/api/v1/ai/ask`) has been upgraded to support context-aware intelligent querying.

## Response Structure
The `AiResponseDto` now strictly conforms to structured intelligence responses:
```json
{
  "prompt": "Explain this fraud analysis.",
  "answer": "...",
  "timestamp": "2023-10-27T10:00:00",
  "sources": ["Analysis ID: 123", "Analysis ID: 456"],
  "confidence": "HIGH",
  "contextUsed": true
}
```

## Internal Workflow
- **Prompt Isolation**: Internal prompt instructions (system prompts, hallucination guards) are generated strictly backend-side by `AiAssistantService`. They are **never** returned to the client.
- **Source Attribution**: The frontend only receives the explicit `sources` array derived from actual database records (via `SecurityContextBuilder`).
- **Secrets Management**: LLM API keys (`ai.provider.key`) remain entirely on the server and are injected via environment properties. Stack traces are caught by the `GlobalExceptionHandler`.
