export interface SecurityEvent {
    eventId: string;
    eventType: 'UPI_TRANSACTION' | 'WINDOWS_EVENT' | 'NETWORK_LOG' | 'CVE_ALERT' | 'GENERIC_ANOMALY';
    source: 'API_GATEWAY' | 'WINDOWS_AGENT' | 'NETWORK_SENSOR' | 'VULNERABILITY_SCANNER' | 'EXTERNAL_FEED';
    timestamp: string;
    severity: 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL' | null;
    correlationId?: string;
    entityReferenceId: string;
    payloadMetadata?: Record<string, any>;
}
