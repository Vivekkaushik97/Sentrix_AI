# Phase 8: Live Events UI

## Implementation
- Added `useLiveSecurityEvents` custom hook that instantiates a standard browser `EventSource` attached to `/api/v1/live/events`.
- Employs lightweight `framer-motion` for a subtle entry slide-and-fade animation, avoiding overly distracting flashing animations while maintaining real-time SOC awareness.
- Status indicator clearly denotes "CONNECTING", "LIVE", and "OFFLINE".
