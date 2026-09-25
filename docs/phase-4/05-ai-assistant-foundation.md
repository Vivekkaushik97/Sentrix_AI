# Phase 4: AI Assistant Foundation

This document outlines the AI Assistant Foundation implemented in Step 5.

## Domain Model
- Uses standard stateless `AiRequestDto` (prompt) and `AiResponseDto` (prompt, answer, timestamp).
- Designed behind an abstraction `AiProvider`.
- Currently utilizes a `DefaultMockAiProvider` that safely responds with a truthful message without fabricating intelligence.

## Configuration
- Respects the rule: "Do NOT claim that an AI answer was generated if no real provider is configured."
- If `ai.provider.key` is missing in properties, the service gracefully indicates it is unconfigured/unavailable.
- No API keys or secrets are committed.

## API Endpoints
- **POST `/api/v1/ai/ask`**
  - Payload: `AiRequestDto`
  - Returns `AiResponseDto` containing the AI answer or the fallback unavailable message.

## Testing
- `AiAssistantControllerIntegrationTest` verifies the JSON contract utilizing `@WebMvcTest`.
