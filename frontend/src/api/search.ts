import axios from 'axios';

export interface SecuritySearchResultDto {
    id: string;
    type: string;
    title: string;
    status: string;
    priority: string;
    timestamp: string;
}

export interface SearchQueryDto {
    query: string;
    typeFilter?: string;
    limit?: number;
}

const API_BASE_URL = '/api/v1/search';

export const searchApi = {
    search: async (query: SearchQueryDto): Promise<SecuritySearchResultDto[]> => {
        const response = await axios.post(API_BASE_URL, query);
        return response.data;
    }
};
