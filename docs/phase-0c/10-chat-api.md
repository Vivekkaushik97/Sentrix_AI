# Chat / AI Assistant API

## Base Path: `/api/v1/chat`

Handles AI interactions.

## Endpoints

### `POST /api/v1/chat/sessions`
* **Purpose**: Start a new chat thread.
* **Response**: `201 Created` (Returns session UUID).

### `GET /api/v1/chat/sessions`
* **Purpose**: List user's chat threads.

### `GET /api/v1/chat/sessions/{sessionId}/messages`
* **Purpose**: Fetch message history.

### `POST /api/v1/chat/sessions/{sessionId}/messages`
* **Purpose**: Send a message to the AI.
* **Process**: Synchronous REST request.
  *(Note: Streaming via SSE/WebSockets is TBD. For now, the contract assumes a blocking REST call where the backend invokes Spring AI and returns the full response).*
* **Request**: `{ "content": "What does CVE-2024 mean?" }`
* **Response**: `200 OK`
  ```json
  {
    "data": {
      "role": "ASSISTANT",
      "content": "...",
      "createdAt": "..."
    }
  }
  ```
