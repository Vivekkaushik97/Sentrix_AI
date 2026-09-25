import type { SecurityIncident, Page } from '../types/incidents';

const API_URL = '/api/v1/incidents';

export const fetchIncidents = async (page: number = 0, size: number = 50): Promise<Page<SecurityIncident>> => {
    const response = await fetch(`${API_URL}?page=${page}&size=${size}`);
    if (!response.ok) {
        throw new Error('Failed to fetch incidents');
    }
    return response.json();
};

export const fetchIncidentById = async (id: string): Promise<SecurityIncident> => {
    const response = await fetch(`${API_URL}/${id}`);
    if (!response.ok) {
        throw new Error('Failed to fetch incident');
    }
    return response.json();
};
