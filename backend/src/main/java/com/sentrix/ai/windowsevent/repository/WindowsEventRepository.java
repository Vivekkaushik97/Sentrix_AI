package com.sentrix.ai.windowsevent.repository;

import com.sentrix.ai.windowsevent.entity.WindowsEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface WindowsEventRepository extends JpaRepository<WindowsEvent, UUID> {
    Page<WindowsEvent> findByComputerName(String computerName, Pageable pageable);
    List<WindowsEvent> findByTimestampBetween(OffsetDateTime start, OffsetDateTime end);
}
