export interface InvestigationEvent {
  id: string;
  eventType: string;
  referenceId: string;
  summary: string;
  createdAt: string;
}

export interface InvestigationCorrelation {
  id: string;
  sourceRecordType: string;
  sourceRecordId: string;
  relatedRecordType: string;
  relatedRecordId: string;
  correlationReason: string;
  confidenceScore: number;
  createdAt: string;
}

export interface Investigation {
  id: string;
  title: string;
  description: string;
  status: string;
  priority: string;
  owner: string | null;
  createdAt: string;
  updatedAt: string;
  closedAt: string | null;
  events: InvestigationEvent[];
  correlations: InvestigationCorrelation[];
}

export interface InvestigationTimelineDto {
  id: string;
  timestamp: string;
  source: string;
  eventType: string;
  summary: string;
  severity: string | null;
  riskScore: number | null;
  relatedEntityId: string;
}

export interface InvestigationCreateDto {
  title: string;
  description: string;
  priority: string;
  owner?: string;
}

export interface InvestigationUpdateDto {
  status?: string;
  priority?: string;
}

export interface InvestigationEventDto {
  eventType: string;
  referenceId: string;
  summary: string;
}

export interface InvestigationCorrelationDto {
  sourceRecordType: string;
  sourceRecordId: string;
  relatedRecordType: string;
  relatedRecordId: string;
  correlationReason: string;
  confidenceScore: number;
}
