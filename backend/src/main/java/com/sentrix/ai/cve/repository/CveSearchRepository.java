package com.sentrix.ai.cve.repository;

import com.sentrix.ai.cve.entity.CveSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CveSearchRepository extends JpaRepository<CveSearch, UUID> {
}
