# Phase 6: Risk Scoring

## Overview
The `RiskScorer` calculates a deterministic, bounded, and explainable 0-100 threat score based on actual observations, avoiding opaque ML hallucinations.

## Methodology
- **Detections**: Each matched rule contributes a predefined amount of risk (e.g. Audit Log Clear = +50).
- **Correlations**: Sequential chains multiply the threat reality (e.g. Failed -> Success Auth = +30).
- **Clamping**: The final sum is clamped strictly between 0 and 100.

## Output
This score maps to the standard Sentrix `AnalysisStatus` / `Severity` paradigms and is rendered directly on the Phase 6 dashboard.
