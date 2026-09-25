export interface WindowsEventDto {
  id: string;
  timestamp: string;
  computerName: string;
  logName: string;
  providerName: string;
  eventId: number;
  level: string;
  task: string;
  user: string;
  rawEvent: string;
}

export interface WindowsEventDetectionDto {
  id: string;
  eventId: string;
  ruleId: string;
  severity: string;
  reason: string;
  evidence: string;
  createdAt: string;
}

export interface WindowsEventCorrelationDto {
  id: string;
  correlationKey: string;
  explanation: string;
  severity: string;
  createdAt: string;
  events: WindowsEventDto[];
}

export interface WindowsEventAnalysisDetailDto {
  id: string;
  riskScore: number;
  severity: string;
  computerName: string;
  detections: WindowsEventDetectionDto[];
  correlations: WindowsEventCorrelationDto[];
}

export interface RawWindowsEventDto {
  timestamp?: string;
  logName: string;
  providerName?: string;
  eventId?: number;
  level?: string;
  task?: string;
  opcode?: string;
  keywords?: string;
  user?: string;
  processId?: number;
  threadId?: number;
  channel?: string;
  message?: string;
  rawEventData?: Record<string, any>;
}

export interface WindowsEventIngestionRequest {
  source: string;
  computerName: string;
  events: RawWindowsEventDto[];
}

export interface WindowsEventResponseDto {
  analysisId: string;
  eventsAnalyzed: number;
  riskScore: number;
  detectionCount: number;
  correlationCount: number;
  status: string;
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
