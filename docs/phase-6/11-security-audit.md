# Phase 6: Security Audit

## Payload Limits
The controller restricts array length to a maximum of `1000` events per ingestion batch. This prevents out-of-memory crashes from malicious or overly aggressive collectors.

## Cross-Site Scripting (XSS)
Raw event data in Windows EVTX logs can contain arbitrary strings injected by attackers. 
- The backend normalizer stores these as pure text.
- Standard React rendering patterns ensure these fields are automatically escaped if and when they are rendered on the frontend.

## No Code Execution
The `WindowsEventNormalizer` and `WindowsRuleEngine` evaluate strings purely deterministically. They do not execute PowerShell blocks found within the event messages.
