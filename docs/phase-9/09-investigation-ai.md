# Phase 9: Investigation AI

## Strategy
Instead of building a separate, autonomous agent, the existing AI foundation (`/api/v1/ai/ask`) is contextualized for the investigation workspace.

## Details
- AI remains **explanatory**.
- The React application (`InvestigationDetail.tsx`) constructs a structured string literal payload containing the investigation's title, description, status, and raw chronological timeline.
- The prompt explicitly asks the AI to identify gaps based strictly on provided data, bypassing hallucinations and removing the need for a secondary RAG backend implementation.
