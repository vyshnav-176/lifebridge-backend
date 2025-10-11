package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.HealthRecord;
import com.lifebridge.lifebridge_backend.repository.HealthRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthRecordService {

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    // Method 1: Save a new health record
    public HealthRecord saveRecord(HealthRecord record) {
        // Simple business logic: just save the record
        return healthRecordRepository.save(record);
    }

    // Method 2: Get all records for a specific user
    public List<HealthRecord> getRecordsByUserId(Long userId) {
        // Use the custom method defined in the Repository
        return healthRecordRepository.findByUserId(userId);
    }
}