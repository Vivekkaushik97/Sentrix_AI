import axios from 'axios';

export interface SecurityPostureDto {
    id: string;
    overallScore: number;
    fraudRisk: number;
    endpointRisk: number;
    vulnerabilityRisk: number;
    incidentRisk: number;
    investigationRisk: number;
    actionRisk: number;
    createdAt: string;
}

const API_BASE_URL = '/api/v1/posture';

export const postureApi = {
    getLatestPosture: async (): Promise<SecurityPostureDto> => {
        const response = await axios.get(API_BASE_URL);
        return response.data;
    }
};
