import { useState, useEffect } from 'react';
import type { SecurityEvent } from '../types/liveEvents';

export const useLiveSecurityEvents = () => {
    const [events, setEvents] = useState<SecurityEvent[]>([]);
    const [status, setStatus] = useState<'CONNECTING' | 'LIVE' | 'OFFLINE'>('CONNECTING');

    useEffect(() => {
        const eventSource = new EventSource('/api/v1/live/events');

        eventSource.onopen = () => {
            setStatus('LIVE');
        };

        eventSource.addEventListener('security-event', (event: MessageEvent) => {
            try {
                const data: SecurityEvent = JSON.parse(event.data);
                setEvents((prev) => [data, ...prev].slice(0, 50)); // Keep last 50 events
            } catch (err) {
                console.error('Failed to parse security event', err);
            }
        });

        eventSource.onerror = () => {
            setStatus('OFFLINE');
            eventSource.close();
        };

        return () => {
            eventSource.close();
        };
    }, []);

    return { events, status };
};
