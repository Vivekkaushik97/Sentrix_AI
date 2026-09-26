# Phase 16: IOC Normalization

## Overview
Implemented deterministic normalization logic for incoming Indicators of Compromise to ensure deduplication.

## Normalization Rules
- **Domains & URLs:** Lowercased, stripped of trailing slashes and common prefixes (where safe for comparison).
- **File Hashes:** Lowercased (MD5, SHA-1, SHA-256).
- **IP Addresses:** Validated via regex to ensure IPv4/IPv6 conformity before insertion. Whitespace trimmed.
- **Malformed Data:** Invalid structures (e.g., an IP address of `999.999.999.999`) are deterministically rejected with HTTP 400 Bad Request. The system never silently attempts to convert junk data into an IOC.
