# Alert Prioritization

## Objective
Establish a deterministic tiering system for incoming security alerts and events to normalize them into actionable priorities.

## Priority Levels
1. **CRITICAL**: Imminent risk of data loss, active fraud confirmed, or critical infrastructure compromise.
2. **HIGH**: High probability of compromise or high-risk vulnerability active on exposed endpoints.
3. **MEDIUM**: Suspicious activity requiring analyst review but without confirmed exploitation.
4. **LOW**: Anomalous activity that deviates from baselines but is likely benign.
5. **INFORMATIONAL**: Standard operational events, successful logins, routine configuration changes.

## Deterministic Scoring Mechanism
The `AlertPrioritizationService` assigns a final Priority level by evaluating:
- **Base Severity**: Extracted from the source system (e.g., Windows Event Severity, CVE CVSS score).
- **Risk Score Correlation**: If the event is correlated with an incident having a high Risk Score, its priority elevates.
- **Recurrence Volume**: High frequency of the same event from the same entity increases priority.
- **Investigation State**: Alerts linked to `OPEN` investigations are prioritized higher than unlinked alerts.

## AI Boundary
AI is strictly prohibited from authoritatively changing an alert's priority level. AI may only provide a **Recommended Priority** with an explanation for why an analyst should manually override the deterministic priority.
