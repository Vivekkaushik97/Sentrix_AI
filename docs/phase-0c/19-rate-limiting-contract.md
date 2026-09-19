# Rate Limiting Contract

Rate limiting prevents abuse of expensive resources (LLMs, ML models). Backed by Redis.

## Conceptual Limits (TBD Final Numbers)
* **Session Initialization**: 5 per minute per IP.
* **Fraud Inference**: 100 per minute per Session.
* **EVTX Upload**: 5 per hour per Session.
* **CVE Lookup/Analysis**: 30 per minute per Session.
* **Chat Messages**: 20 per minute per Session.

## Behavior
* Backend uses a Redis-based token bucket or sliding window algorithm.
* Exceeding limits returns `429 Too Many Requests`.
* Include standard headers: `X-RateLimit-Limit`, `X-RateLimit-Remaining`.
