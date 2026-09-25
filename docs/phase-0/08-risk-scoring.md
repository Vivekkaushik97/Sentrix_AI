# Phase 0: Risk Scoring Contract

This document outlines the standard risk scoring framework used across all Sentrix AI modules to provide a consistent user experience.

## The Normalization Concept

Different security domains have wildly different native metrics:
*   **Fraud**: Machine learning probability (0.0 to 1.0).
*   **CVE**: CVSS Base Score (0.0 to 10.0).
*   **Event Logs**: Rule-based counts and heuristic severity (e.g., 5 critical events, 20 warning events).

To present a unified dashboard and history, every module MUST convert its native output into the standard Sentrix AI `RiskProfile` object.

## The Standard RiskProfile DTO

```java
public class RiskProfile {
    private Integer riskScore;       // 0 to 100
    private Severity severity;       // LOW, MEDIUM, HIGH, CRITICAL
    private String classification;   // Short description (e.g., "High Probability of UPI Fraud")
    private Confidence confidence;   // LOW, MEDIUM, HIGH (Confidence in the score)
}

public enum Severity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

public enum Confidence {
    LOW,
    MEDIUM,
    HIGH
}
```

## Module-Specific Thresholds (TO BE FINALIZED)

The exact algorithms for mapping native metrics to the 0-100 scale and Severity enum are **TO BE FINALIZED DURING MODULE IMPLEMENTATION**. The following are preliminary concepts:

### 1. UPI Fraud
*   **Native**: ML Probability (0.0 - 1.0)
*   **Mapping (Example)**:
    *   Probability 0.0 - 0.4 -> Score 0-40, LOW
    *   Probability 0.41 - 0.7 -> Score 41-70, MEDIUM
    *   Probability 0.71 - 0.9 -> Score 71-90, HIGH
    *   Probability 0.91 - 1.0 -> Score 91-100, CRITICAL

### 2. CVE
*   **Native**: CVSS v3.1 Score (0.0 - 10.0)
*   **Mapping (Example)**:
    *   Multiply CVSS by 10.
    *   0 - 39 -> LOW
    *   40 - 69 -> MEDIUM
    *   70 - 89 -> HIGH
    *   90 - 100 -> CRITICAL

### 3. Windows Event Logs
*   **Native**: Aggregated rule violations.
*   **Mapping (Example)**:
    *   Needs a weighted heuristic scoring algorithm. (e.g., 1 Critical Event = +50 score, 1 Warning = +10 score). Capped at 100.
    *   Algorithm requires tuning based on real EVTX samples.

## Consistency Rule
No module is permitted to expose raw probabilities or CVSS scores directly as the primary "Sentrix Risk Score". The native scores can be displayed in the detailed view, but the normalized `RiskProfile` must be used for all lists, summaries, and dashboards.
