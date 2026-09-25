export type ActionType = 
    | 'REVIEW_TRANSACTION'
    | 'FLAG_TRANSACTION'
    | 'CREATE_INVESTIGATION'
    | 'ADD_INVESTIGATION_NOTE'
    | 'CREATE_SECURITY_INCIDENT'
    | 'ESCALATE_INCIDENT'
    | 'REQUEST_MANUAL_REVIEW'
    | 'REFRESH_CVE_INTELLIGENCE'
    | 'GENERATE_SECURITY_REPORT'
    | 'MARK_EVENT_FOR_REVIEW';

export const ActionStatus = {
    PROPOSED: 'PROPOSED',
    PENDING_APPROVAL: 'PENDING_APPROVAL',
    APPROVED: 'APPROVED',
    QUEUED: 'QUEUED',
    EXECUTING: 'EXECUTING',
    COMPLETED: 'COMPLETED',
    REJECTED: 'REJECTED',
    CANCELLED: 'CANCELLED',
    FAILED: 'FAILED',
    EXPIRED: 'EXPIRED'
} as const;

export type ActionStatusType = typeof ActionStatus[keyof typeof ActionStatus];

export type ActionPriority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';

export interface SecurityAction {
    id: string;
    actionType: ActionType;
    status: ActionStatusType;
    priority: ActionPriority;
    targetReference?: string;
    reason?: string;
    proposedBy?: string;
    investigationId?: string;
    incidentId?: string;
    createdAt: string;
    updatedAt: string;
    expiresAt?: string;
}

export interface ActionAuditEntry {
    id: string;
    actionId: string;
    actor: string;
    eventType: string;
    reason?: string;
    createdAt: string;
}

export interface ActionRequestDto {
    actionType: ActionType;
    targetReference?: string;
    reason?: string;
    investigationId?: string;
    incidentId?: string;
}

export interface ApprovalRequestDto {
    reason: string;
}
