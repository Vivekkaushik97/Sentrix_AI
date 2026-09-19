# Chat Database Model

## Design
* **`chat_sessions`**: Owned by the `anonymous_session`. Represents a distinct conversation thread.
* **`chat_messages`**: Belongs to a chat session. Ordered chronologically via `created_at`.

## Message Storage
* `role`: Enum (`USER`, `ASSISTANT`, `SYSTEM`).
* `content`: The raw markdown/text of the message.

## Auditability & Secrets
* **Tokens**: AI API keys (OpenAI, etc.) are strictly kept in Environment Variables and NEVER stored in the database.
* **Reproducibility**: Token usage or specific model versions per message are deemed unnecessary for the initial phase to keep the schema lean, but could be added as metadata later.

The schema enables standard Chat UI flows (fetching a session, mapping over messages) compatible with standard Spring AI prompt building.
