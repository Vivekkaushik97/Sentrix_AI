import type { SecurityContextDto } from '../types/context';

const API_BASE = '/api/v1/context';

export const getSecurityContext = async (sourceType: string, sourceId: string): Promise<SecurityContextDto> => {
  const res = await fetch(`${API_BASE}/${sourceType}/${sourceId}`);
  if (!res.ok) throw new Error('Failed to fetch security context');
  const json = await res.json();
  return json.data;
};
