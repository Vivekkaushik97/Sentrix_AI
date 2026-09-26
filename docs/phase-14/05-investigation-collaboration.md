# Phase 14: Multi-User Investigations

## Overview
Upgraded the Investigation workspace to support collaborative enterprise operations.

## Architecture
- **Schema Update (V11):** 
  - `owner_id`: Tracks the authoritative owner of the investigation.
  - `assigned_analyst_id`: Tracks the current analyst actively working the investigation.
  - `investigation_collaborators`: A mapping table for other users watching or contributing to the investigation.
- **Rules:**
  - Analysts can self-assign unassigned investigations.
  - `SECURITY_MANAGER` can force-reassign investigations.
  - Notes and events added to the investigation inherently log the authenticated user ID of the actor.
  - Unassigned state is represented by `null`, and no fake users are ever assigned.
