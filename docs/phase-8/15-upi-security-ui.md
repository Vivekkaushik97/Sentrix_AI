# Phase 8: UPI Security UI

## Components
- `/upi-security` splits the view into a Transaction History listing and a Submission form.
- Form inputs expect valid JSON payloads.
- **No Fake Data**: Employs the `EmptyState` component when 0 transactions exist. Does not invent "Active attacks: 12" logic.
- Colors mapped deterministicly to `Severity` enums (`CRITICAL` -> Red, etc).
