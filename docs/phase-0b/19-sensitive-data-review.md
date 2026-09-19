# Sensitive Data Review

Even without registered users, metadata can leak sensitive information.

### Findings
1. **IP Addresses**: `anonymous_sessions.ip_hash`. IP addresses should NOT be stored in plaintext as they are considered PII under GDPR. They must be hashed (e.g., SHA-256 with a daily salt) if required for rate limiting.
2. **Transaction Features**: `fraud_transactions`. Banking datasets often contain PII. The system must enforce that the frontend sends *anonymized* features (e.g., VPA hashes, amounts) rather than raw banking identifiers.
3. **Uploaded Event Logs**: `.evtx` files can contain usernames, hostnames, and internal IPs. The application does not index PII from these logs, but the raw file must be aggressively deleted post-analysis (see Retention).
4. **Chat Logs**: Users might paste sensitive data into the AI chat. The database will store this as plaintext `content`. This data is tied to the anonymous session and is destroyed when the session expires, mitigating long-term exposure.

**Verdict**: The schema strictly avoids dedicated PII columns (No emails, passwords, names).
