package com.sentrix.ai.security.incident.repository;

import com.sentrix.ai.security.incident.entity.SecurityIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SecurityIncidentRepository extends JpaRepository<SecurityIncident, UUID> {
}
