# Phase 6: Event Normalization

## Overview
The `WindowsEventNormalizer` is responsible for transforming raw JSON representations of EVTX records into canonical `WindowsEvent` entities.

## Normalization Steps
1. **Timestamp Fallback**: If an event arrives missing a timestamp (due to corruption), the ingestion time is used as a fallback.
2. **EventID Guarantee**: Event ID 0 is used as a fallback if the ID is missing, preventing database null-constraint crashes.
3. **Raw Data Preservation**: Any unstructured, arbitrary key/value pairs pushed by the collector are serialized into the `raw_event` text column. This ensures no forensic data is lost during normalization.
4. **Security/Error Handling**: Serialization failures on the raw payload are caught and logged gracefully rather than failing the entire batch ingestion transaction.
