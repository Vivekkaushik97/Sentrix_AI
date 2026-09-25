# Phase 6: Correlation Engine

## Overview
The `CorrelationEngine` links disparate events into a unified storyline.

## Implemented Sequence
- **Brute Force Compromise**: Correlates a Failed Authentication (4625) with a subsequent Successful Authentication (4624) for the same user.
- **Output**: Generates a `WindowsEventCorrelation` entity, grouping both events under a HIGH severity flag with a unified explanation.

## Design Rule
Correlations are informational and contribute to risk scoring. The system explicitly avoids declaring an "attack" unilaterally, following the "No fake security statistics" rule.
