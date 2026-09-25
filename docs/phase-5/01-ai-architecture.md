# Phase 5: AI Architecture

## Overview
The Phase 5 AI Architecture defines a provider-agnostic layer for injecting intelligent reasoning into Sentrix AI. It utilizes a `SecurityContextBuilder` to fetch truthful database context before constructing a strict prompt for the LLM.

## Components
- **`AiProvider` Interface**: Abstraction for LLM vendors (OpenAI, Anthropic, Mock).
- **`DefaultMockAiProvider`**: A safe fallback that prevents hallucinations when no external API key is present.
- **`AiAssistantService`**: The orchestrator that merges the user's prompt with the context and enforces system instructions.
- **`SecurityContextBuilder`**: Responsible for querying the database to bound the AI's knowledge to real events.

## Hallucination Safeguards
- The system prompt explicitly instructs the AI to *never* invent security facts.
- It is commanded to return "Insufficient data to answer" if the context is empty or lacks relevance.
- These controls prevent the AI from fabricating fake transactions, CVEs, or statistics.
