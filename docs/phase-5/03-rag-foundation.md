# Phase 5: RAG Foundation

## Overview
A provider-agnostic Retrieval-Augmented Generation (RAG) abstraction has been introduced in the `com.sentrix.ai.rag` package.

## Components
- **`Document` & `DocumentChunk`**: Define text structure.
- **`EmbeddingProvider`**: Interface for embedding text (OpenAI, HuggingFace, Mock).
- **`VectorStore`**: Abstraction for a vector database. An `InMemoryVectorStore` was implemented to avoid forcing a premature infrastructure migration (e.g. pgvector or Pinecone) as per Phase 5 guidelines.
- **`RetrievalService`**: Orchestrates taking a query, embedding it, and returning the top matching `RetrievalResult` list.

## Usage
Currently acts as a foundation. It can be easily extended to store incoming CVEs and Threat Intel reports into chunks to provide deeper semantic search for the AI Assistant, without altering the core Sentrix architecture.
