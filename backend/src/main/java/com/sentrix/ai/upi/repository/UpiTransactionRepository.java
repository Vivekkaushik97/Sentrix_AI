package com.sentrix.ai.upi.repository;

import com.sentrix.ai.upi.entity.UpiTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UpiTransactionRepository extends JpaRepository<UpiTransaction, UUID> {
    List<UpiTransaction> findByPayerVpaAndTimestampBetween(String payerVpa, OffsetDateTime start, OffsetDateTime end);
    List<UpiTransaction> findByDeviceIdAndTimestampBetween(String deviceId, OffsetDateTime start, OffsetDateTime end);
}
