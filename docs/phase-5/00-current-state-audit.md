# Phase 5: Current State Audit

## Overview
Phase 5 introduces the Sentrix AI Intelligence Layer, adding RAG, context building, and intelligent querying over existing data. 
Before implementing, this audit identifies what is currently available from prior phases and what needs to be implemented.

## 1. Existing Architecture & Foundation (Completed in Phases 1-4)
- **Java 25 & Spring Boot 3.5.4**: Verified and working.
- **Supabase PostgreSQL**: Live, tested, and actively utilized by Fraud, History, and Report APIs.
- **Database Migrations (Flyway)**: Clean baseline established in `V1__init_schema.sql`.
- **Redis**: Available and configured. Currently used (or intended) for Session management and CVE caching.
- **RabbitMQ**: Integrated into `application.yml` and `pom.xml`, but no queues/listeners are actively processing domain data yet.
- **Frontend (React + Vite)**: Phase 2 UI established. Dashboard metrics API integrated.
- **Domain Entities**: `Analysis`, `FraudAnalysis`, `EventLogAnalysis`, `CveSearch`, `Report`.
- **APIs Established**: `/api/v1/fraud`, `/api/v1/event-logs`, `/api/v1/cves`, `/api/v1/dashboard`, `/api/v1/reports`, `/api/v1/history`, `/api/v1/ai`.

## 2. AI & Intelligence Current State
- **AI Interface (`AiProvider`)**: Defines `generateResponse(AiRequestDto request)`.
- **Mock Provider (`DefaultMockAiProvider`)**: Returns a safe string indicating whether an API key is configured.
- **Service (`AiAssistantService`)**: A simple passthrough that takes an `AiRequestDto` and calls the provider.
- **Controller (`AiAssistantController`)**: Exposes `POST /api/v1/ai/ask`.

## 3. Missing Functionality for Phase 5
1. **Context Builder**: No mechanism exists to query Fraud/EventLog/CVE data to build a prompt context.
2. **Provider Agnostic LLM Client**: Needs to interact with a real LLM (like OpenAI/Anthropic/Gemini) if a key is provided, while still supporting the mock.
3. **RAG Foundation**: Missing abstractions (`Document`, `VectorStore`, `EmbeddingProvider`, `RetrievalService`).
4. **Knowledge Ingestion**: No way to chunk and store text data into the VectorStore.
5. **AI API Expansion**: The `AiResponseDto` lacks structured fields like `sources`, `confidence`, and `contextUsed`.
6. **Hallucination Safeguards**: The prompt logic must instruct the LLM strictly not to invent facts and must handle insufficient data gracefully.
7. **Frontend Integration**: The UI must display the real backend responses, loading states, and handle the structured output (sources).

## 4. Execution Plan
- **Step 1**: Define AI Architecture (Update `AiProvider` and add Context/Prompt abstractions).
- **Step 2**: Security Intelligence Context (Build services to fetch existing Postgres records).
- **Step 3 & 4**: RAG Foundation & Ingestion (Create clean interfaces without forcing a heavy new vector DB unless required—perhaps simple in-memory or Postgres text search for now).
- **Step 5 & 6**: AI Assistant API & Safety (Expand DTOs, construct prompt templates with strict hallucination limits).
- **Step 7 & 8**: Redis & RabbitMQ Evaluation (Decide on async ingestion/caching).
- **Step 9 & 10**: Frontend Integration.
- **Step 11-15**: Testing, Auditing, and Documentation.
