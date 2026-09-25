# Phase 5: Security Intelligence Context Engine

## Overview
The Security Context Engine aggregates recent analyses from the database to give the AI real-world grounding.

## Context Retrieval Strategy
- Currently, the `SecurityContextBuilder` queries the `AnalysisRepository` for the 5 most recent analyses.
- The `AiAssistantService` then packages this into a strict instruction set for the `AiProvider`.
- By passing precise facts (Severity, Type, Timestamp, ID) into the prompt, the AI can perform summarization and interpretation strictly on actual Sentrix data.

## Future Expansion
- RAG (Retrieval-Augmented Generation) will plug directly into this engine.
- Instead of just fetching the top 5 recent events, semantic search will allow retrieval of highly relevant documents across logs, CVEs, and past fraud analyses based on the exact user question.
