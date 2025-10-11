package com.lifebridge.lifebridge_backend.repository;

import com.lifebridge.lifebridge_backend.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {

    // Spring Data JPA magic: automatically creates a query to find all records by user ID
    List<HealthRecord> findByUserId(Long userId);
}