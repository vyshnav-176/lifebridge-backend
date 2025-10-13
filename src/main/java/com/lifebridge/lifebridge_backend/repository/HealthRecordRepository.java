package com.lifebridge.lifebridge_backend.repository;

import com.lifebridge.lifebridge_backend.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    HealthRecord findByUser_Id(Long userId);
}