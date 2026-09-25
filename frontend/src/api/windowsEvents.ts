import type { 
  WindowsEventIngestionRequest, 
  WindowsEventResponseDto,
  WindowsEventDto,
  WindowsEventAnalysisDetailDto,
  WindowsEventDetectionDto,
  WindowsEventCorrelationDto,
  Page
} from '../types/windowsEvents';

const BASE_URL = '/api/v1/windows-events';

export const ingestWindowsEvents = async (request: WindowsEventIngestionRequest): Promise<{ data: WindowsEventResponseDto, message: string }> => {
  const response = await fetch(`${BASE_URL}/ingest`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(request)
  });
  const result = await response.json();
  if (!response.ok) throw new Error(result.message || 'Failed to ingest events');
  return result;
};

export const fetchWindowsEvents = async (page = 0, size = 100): Promise<{ data: Page<WindowsEventDto>, message: string }> => {
  const response = await fetch(`${BASE_URL}?page=${page}&size=${size}`);
  const result = await response.json();
  if (!response.ok) throw new Error(result.message || 'Failed to fetch events');
  return result;
};

export const fetchWindowsAnalysis = async (id: string): Promise<{ data: WindowsEventAnalysisDetailDto, message: string }> => {
  const response = await fetch(`${BASE_URL}/${id}`);
  const result = await response.json();
  if (!response.ok) throw new Error(result.message || 'Failed to fetch analysis');
  return result;
};

export const fetchWindowsDetections = async (): Promise<{ data: WindowsEventDetectionDto[], message: string }> => {
  const response = await fetch(`${BASE_URL}/detections`);
  const result = await response.json();
  if (!response.ok) throw new Error(result.message || 'Failed to fetch detections');
  return result;
};

export const fetchWindowsCorrelations = async (): Promise<{ data: WindowsEventCorrelationDto[], message: string }> => {
  const response = await fetch(`${BASE_URL}/correlations`);
  const result = await response.json();
  if (!response.ok) throw new Error(result.message || 'Failed to fetch correlations');
  return result;
};
