package com.sentrix.ai.windowsevent.repository;

import com.sentrix.ai.windowsevent.entity.WindowsEventCorrelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WindowsEventCorrelationRepository extends JpaRepository<WindowsEventCorrelation, UUID> {
}
