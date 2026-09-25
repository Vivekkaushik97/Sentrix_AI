# Phase 0: AI / RAG Architecture

This document defines the conceptual architecture for the Artificial Intelligence and Retrieval-Augmented Generation (RAG) components of Sentrix AI. It explicitly separates the responsibilities of the General AI Assistant, Domain Explanations, and the RAG Knowledge Base.

## 1. Provider & Abstraction
* **Framework**: Spring AI (acts as the abstraction layer).
* **LLM Provider**: Configurable (e.g., OpenAI, Anthropic, Ollama for local testing). Hardcoding a specific provider is forbidden; use application properties.
* **Embedding Provider**: Configurable via Spring AI.

## 2. AI Responsibilities (Non-RAG)
The system uses the LLM for domain-specific explanations, separate from the chat interface.

* **CVE Explanation**: Prompts the LLM with raw NVD JSON to generate a plain-English summary of the vulnerability and mitigation steps.
* **Fraud Explanation**: Prompts the LLM with transaction details and the ML probability score to explain *why* a transaction was flagged as fraudulent.
* **Event Log Explanation**: Prompts the LLM with aggregated threat indicators to summarize an attack chain found in an EVTX file.
* **Report Assistance**: Generates executive summaries for PDF security reports based on analysis data.

*Prompt Construction Concept*:
```
System: You are an expert cybersecurity analyst.
User: Explain this CVE to a non-technical manager. Data: {cve_json}
```

## 3. RAG Architecture (Knowledge Base Chatbot)
The AI Cybersecurity Assistant uses RAG to ground its answers in verified cybersecurity documentation, reducing hallucinations.

### A. Ingestion Pipeline (Asynchronous)
1. **Document Upload**: PDF/Markdown containing cybersecurity guidelines, playbooks, or threat intel.
2. **Validation**: Validate file type and content to prevent RAG poisoning.
3. **Text Extraction**: Parse text from the document.
4. **Chunking**: Split text into semantic chunks (e.g., Spring AI `TokenTextSplitter`). Chunk size and overlap are **TO BE DECIDED**.
5. **Embedding**: Pass chunks to the Embedding Model to generate vector representations. Dimensions are **TO BE DECIDED** based on model.
6. **Storage**: Save chunks and vectors to `knowledge_chunks` and `knowledge_embeddings` in Supabase (pgvector).

### B. Retrieval Pipeline (Synchronous)
1. **User Query**: User asks, "How do I mitigate pass-the-hash attacks?"
2. **Query Embedding**: The query is converted to a vector using the same Embedding Model.
3. **Similarity Search (pgvector)**: The system queries Supabase for the top `K` most similar vectors (e.g., Top-K = 5) where distance is below a relevance threshold (Threshold **TO BE DECIDED**).
4. **Context Assembly**: The retrieved text chunks are assembled into a context block.
5. **Prompt Construction**:
   ```
   System: You are an AI Cybersecurity Assistant. Answer the user's question using ONLY the provided Context. If the context does not contain the answer, say "I don't have enough information."
   Context: {retrieved_chunks}
   User: {user_query}
   ```
6. **LLM Invocation**: Send the prompt to the LLM.
7. **Response & Attribution**: Return the response along with citations (document metadata from the retrieved chunks).

## 4. Security Considerations
* **Prompt Injection Defenses**: The system prompt must explicitly instruct the LLM to ignore subsequent instructions to "ignore previous instructions".
* **Data Isolation**: The LLM must not have access to raw database queries. All context must be strictly controlled by the Java backend.
* **RAG Poisoning**: Only administrators (or trusted sources) can ingest documents into the Knowledge Base.
* **PII Redaction**: Ensure user queries and context blocks are stripped of sensitive personal data before being sent to external LLM providers.
