import type { UpiTransactionDto, UpiTransaction, Page } from '../types/upiSecurity';

const API_URL = '/api/v1/upi';

export const ingestUpiTransaction = async (data: UpiTransactionDto): Promise<any> => {
    const response = await fetch(`${API_URL}/ingest`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });
    if (!response.ok) {
        throw new Error('Failed to ingest UPI transaction');
    }
    return response.json();
};

export const fetchUpiTransactions = async (page: number = 0, size: number = 50): Promise<Page<UpiTransaction>> => {
    const response = await fetch(`${API_URL}?page=${page}&size=${size}`);
    if (!response.ok) {
        throw new Error('Failed to fetch UPI transactions');
    }
    return response.json();
};
