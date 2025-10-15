package com.lifebridge.lifebridge_backend.repository;

import com.lifebridge.lifebridge_backend.entity.HealthLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthLogRepository extends JpaRepository<HealthLog, Long> {
    // Find all logs by user ID, ordered by timestamp in descending order (most recent first)
    List<HealthLog> findByUser_IdOrderByTimestampDesc(Long userId);
}
