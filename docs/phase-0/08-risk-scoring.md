# Risk Scoring Contract

Sentrix AI implements a normalized Risk Scoring framework to unify diverse security analysis outputs.

## Conceptual Framework
Every analysis module (Fraud, Event Logs, CVE) must map its internal findings to this common representation before persisting to `analysis_records`.

* **Raw Score**: `0` to `100` (integer).
* **Classification**: Enum (`LOW`, `MEDIUM`, `HIGH`, `CRITICAL`).

### Base Thresholds (Subject to tuning)
* `0 - 24`: **LOW** (Informational, baseline activity)
* `25 - 49`: **MEDIUM** (Anomalous, warrants observation)
* `50 - 74`: **HIGH** (Likely threat, action recommended)
* `75 - 100`: **CRITICAL** (Severe threat, immediate action required)

## Module-Specific Mapping (TO BE FINALIZED DURING IMPLEMENTATION)

### UPI Fraud Detection
* Output: ML Probability `0.0` to `1.0`.
* Mapping: `score = probability * 100`.

### Windows Event Log Analysis
* Output: Aggregate of individual event severities.
* Mapping: Requires algorithmic definition based on the number of critical events vs total events. (TO BE FINALIZED).

### CVE Intelligence
* Output: CVSS v3.1 Base Score (`0.0` to `10.0`).
* Mapping: `score = CVSS * 10`.

This normalization ensures the frontend Dashboard and History views can sort, filter, and color-code threats universally without knowing the specifics of the underlying analysis engine.
