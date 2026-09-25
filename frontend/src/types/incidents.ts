export interface SecurityIncident {
    id: string;
    title: string;
    description?: string;
    severity: 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
    status: string;
    riskScore: number;
    createdAt: string;
    updatedAt: string;
    events: IncidentEvent[];
}

export interface IncidentEvent {
    id: string;
    eventType: string;
    entityReferenceId: string;
    reason: string;
    timestamp: string;
}

export interface Page<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
}
