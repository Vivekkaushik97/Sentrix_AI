import type { Investigation, InvestigationTimelineDto, InvestigationCreateDto, InvestigationUpdateDto, InvestigationEventDto, InvestigationCorrelationDto } from '../types/investigations';

const API_BASE = '/api/v1/investigations';

export const fetchInvestigations = async (page = 0, size = 50): Promise<{ content: Investigation[], totalElements: number }> => {
  const res = await fetch(`${API_BASE}?page=${page}&size=${size}`);
  if (!res.ok) throw new Error('Failed to fetch investigations');
  return res.json();
};

export const getInvestigation = async (id: string): Promise<Investigation> => {
  const res = await fetch(`${API_BASE}/${id}`);
  if (!res.ok) throw new Error('Failed to fetch investigation');
  return res.json();
};

export const createInvestigation = async (data: InvestigationCreateDto): Promise<Investigation> => {
  const res = await fetch(API_BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error('Failed to create investigation');
  const json = await res.json();
  return json.data;
};

export const updateInvestigation = async (id: string, data: InvestigationUpdateDto): Promise<Investigation> => {
  const res = await fetch(`${API_BASE}/${id}`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error('Failed to update investigation');
  const json = await res.json();
  return json.data;
};

export const getInvestigationTimeline = async (id: string): Promise<InvestigationTimelineDto[]> => {
  const res = await fetch(`${API_BASE}/${id}/timeline`);
  if (!res.ok) throw new Error('Failed to fetch timeline');
  return res.json();
};

export const addInvestigationEvent = async (id: string, data: InvestigationEventDto): Promise<Investigation> => {
  const res = await fetch(`${API_BASE}/${id}/events`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error('Failed to add event');
  const json = await res.json();
  return json.data;
};

export const addInvestigationCorrelation = async (id: string, data: InvestigationCorrelationDto): Promise<Investigation> => {
  const res = await fetch(`${API_BASE}/${id}/correlations`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error('Failed to add correlation');
  const json = await res.json();
  return json.data;
};
