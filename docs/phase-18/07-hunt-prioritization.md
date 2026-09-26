# Phase 18: Hunt Prioritization

## Overview
Established deterministic priority ranking (`INFORMATIONAL`, `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`) for active hunts.

## Formula
- Priority is calculated based on factual backend signals: associated incident severities, volume of high-confidence IOC matches, and campaign relevance.
- **AI Rule:** AI may explain the output of the priority calculation, but it is not the authoritative source of the priority tier.
