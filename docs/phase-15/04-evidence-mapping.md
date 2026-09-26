# Phase 15: Compliance Evidence Mapping

## Overview
Enabled many-to-many linkages between raw evidence and abstract compliance controls.

## Architecture
- **Schema (`control_evidence_mapping`):** Links a `control_id` to an `evidence_id`, logging the `mapped_by_id` (the actor) and timestamp.
- **Auditing:** Attaching or removing evidence from a control fires an `enterprise_audit_logs` event.
- **APIs:** Endpoints `/api/compliance/controls/{id}/evidence` provide strictly authorized mechanisms for `SECURITY_MANAGER` and `ADMIN` users to manage this linkage. `VIEWER` users can only list the mappings.
