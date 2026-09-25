import axios from 'axios';
import type { SecurityAction, ActionAuditEntry, ActionRequestDto, ApprovalRequestDto } from '../types/actions';

const API_BASE_URL = '/api/v1/actions';

export const actionApi = {
    listActions: async (): Promise<SecurityAction[]> => {
        const response = await axios.get(API_BASE_URL);
        return response.data;
    },

    getAction: async (id: string): Promise<SecurityAction> => {
        const response = await axios.get(`${API_BASE_URL}/${id}`);
        return response.data;
    },

    createAction: async (dto: ActionRequestDto): Promise<SecurityAction> => {
        const response = await axios.post(API_BASE_URL, dto);
        return response.data;
    },

    requestApproval: async (id: string): Promise<void> => {
        await axios.post(`${API_BASE_URL}/${id}/request-approval`);
    },

    approveAction: async (id: string, dto: ApprovalRequestDto): Promise<void> => {
        await axios.post(`${API_BASE_URL}/${id}/approve`, dto);
    },

    rejectAction: async (id: string, dto: ApprovalRequestDto): Promise<void> => {
        await axios.post(`${API_BASE_URL}/${id}/reject`, dto);
    },

    cancelAction: async (id: string, dto: ApprovalRequestDto): Promise<void> => {
        await axios.post(`${API_BASE_URL}/${id}/cancel`, dto);
    },

    executeAction: async (id: string): Promise<void> => {
        await axios.post(`${API_BASE_URL}/${id}/execute`);
    },

    getAuditTrail: async (id: string): Promise<ActionAuditEntry[]> => {
        const response = await axios.get(`${API_BASE_URL}/${id}/audit`);
        return response.data;
    }
};
