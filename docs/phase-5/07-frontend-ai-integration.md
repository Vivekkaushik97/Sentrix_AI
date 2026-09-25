# Phase 5: Frontend AI Integration

## Overview
The `AIAssistant.tsx` page has been updated from a static empty state to a fully functional interface capable of talking to the backend Intelligence Layer.

## Implemented Features
- **Prompt Input**: A sleek chat input field that accepts user questions.
- **Loading State**: Displays a pulsating "Analyzing context..." message while waiting for the LLM.
- **Response Rendering**: Displays the `answer` alongside the dynamically calculated `confidence` level.
- **Source Context Display**: Renders the `sources` array returned by the `SecurityContextBuilder` so the user knows exactly what data the AI used to formulate its response.
- **Error Handling**: Uses React state to catch and display HTTP error messages gracefully.

## Constraints Followed
- Used existing Phase 2 UI elements and visual identity (Lucide icons, standard Sentrix colors).
- Explicitly states that the responses are based on the system's "real data" via the mock/real provider.
