package com.lifebridge.lifebridge_backend.repository;

import com.lifebridge.lifebridge_backend.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {

    // Corrected query to find by the 'id' property of the 'user' object
    List<HealthRecord> findByUser_Id(Long userId);
}