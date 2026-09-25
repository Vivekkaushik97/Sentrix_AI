import type { InvestigationTimelineDto } from './investigations';

export interface SecurityContextNodeDto {
  id: string;
  type: string;
  label: string;
  severity?: string;
  riskScore?: number;
}

export interface SecurityContextEdgeDto {
  sourceId: string;
  targetId: string;
  relationshipType: string;
  provenance: string;
}

export interface SecurityContextGraphDto {
  nodes: SecurityContextNodeDto[];
  edges: SecurityContextEdgeDto[];
}

export interface SecurityContextDto {
  source: string;
  entityId: string;
  riskScore?: number;
  severity?: string;
  graph: SecurityContextGraphDto;
  timeline: InvestigationTimelineDto[];
  provenance: string[];
}
