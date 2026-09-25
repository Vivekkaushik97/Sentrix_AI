export interface UpiTransactionDto {
    transactionId: string;
    timestamp?: string;
    amount: number;
    currency?: string;
    payerVpa: string;
    payeeVpa: string;
    deviceId?: string;
    ipAddress?: string;
    status: string;
    metadata?: Record<string, any>;
}

export interface UpiTransaction {
    id: string;
    transactionId: string;
    timestamp: string;
    amount: number;
    currency: string;
    payerVpa: string;
    payeeVpa: string;
    deviceId?: string;
    ipAddress?: string;
    status: string;
    riskScore: number;
    severity: 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
    rawMetadata?: string;
    createdAt: string;
}

export interface Page<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
}
