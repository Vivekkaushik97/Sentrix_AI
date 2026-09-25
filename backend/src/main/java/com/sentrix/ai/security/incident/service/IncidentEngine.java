package com.sentrix.ai.security.incident.service;

import com.sentrix.ai.common.enums.Severity;
import com.sentrix.ai.security.event.SecurityEvent;
import com.sentrix.ai.security.event.SecurityEventProcessor;
import com.sentrix.ai.security.event.SecurityEventType;
import com.sentrix.ai.security.incident.entity.IncidentEvent;
import com.sentrix.ai.security.incident.entity.SecurityIncident;
import com.sentrix.ai.security.incident.repository.SecurityIncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class IncidentEngine implements SecurityEventProcessor {

    private static final Logger logger = LoggerFactory.getLogger(IncidentEngine.class);
    private final SecurityIncidentRepository incidentRepository;

    public IncidentEngine(SecurityIncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Override
    public boolean supports(SecurityEventType eventType) {
        // Incident Engine supports all event types. It listens globally to correlate.
        return true; 
    }

    @Override
    @Transactional
    public void process(SecurityEvent event) {
        // Basic correlation logic.
        // In a real system, you'd query active open incidents matching the entityReferenceId.
        // For demonstration, if we see a CRITICAL severity event, we spawn an incident.
        // E.g., CRITICAL Windows Event or HIGH risk UPI transaction triggers a correlated incident.

        if (event.getSeverity() == Severity.CRITICAL || event.getSeverity() == Severity.HIGH) {
            SecurityIncident incident = new SecurityIncident();
            incident.setTitle("Correlated Threat: " + event.getEventType());
            incident.setDescription("Automatic incident spawned due to high-risk event detection across boundaries.");
            incident.setSeverity(event.getSeverity());
            incident.setRiskScore(event.getSeverity() == Severity.CRITICAL ? 90 : 70);

            IncidentEvent ie = new IncidentEvent();
            ie.setEventType(event.getEventType());
            ie.setEntityReferenceId(event.getEntityReferenceId());
            ie.setReason("Triggered initial alert sequence for " + event.getEventType());
            ie.setIncident(incident);
            
            incident.getEvents().add(ie);

            incidentRepository.save(incident);
            logger.info("Spawned new Security Incident {} from event {}", incident.getId(), event.getEventId());
        }
    }
}
