const API_BASE = '/api/v1/ai';

export interface AiRequest {
  query: string;
  context: string;
}

export interface AiResponse {
  response: string;
  confidence: number;
}

export const askAi = async (data: AiRequest): Promise<AiResponse> => {
  const res = await fetch(`${API_BASE}/ask`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error('Failed to query AI');
  const json = await res.json();
  return json.data;
};
