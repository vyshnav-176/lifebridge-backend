package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.HealthRecord;
import com.lifebridge.lifebridge_backend.entity.User;
import com.lifebridge.lifebridge_backend.repository.HealthRecordRepository;
import com.lifebridge.lifebridge_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HealthRecordService {

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    @Autowired
    private UserRepository userRepository;

    // Method to save a new record (used by POST)
    public HealthRecord saveRecord(Long userId, HealthRecord record) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            record.setUser(userOptional.get());
            return healthRecordRepository.save(record);
        }
        throw new RuntimeException("User not found with ID: " + userId);
    }

    // NEW: Method to update an existing record
    public HealthRecord updateRecord(Long userId, HealthRecord updatedRecord) {
        HealthRecord existingRecord = healthRecordRepository.findByUser_Id(userId);
        if (existingRecord != null) {
            existingRecord.setBloodGroup(updatedRecord.getBloodGroup());
            existingRecord.setAllergies(updatedRecord.getAllergies());
            existingRecord.setMedicalConditions(updatedRecord.getMedicalConditions());
            existingRecord.setHeight(updatedRecord.getHeight());
            existingRecord.setWeight(updatedRecord.getWeight());
            return healthRecordRepository.save(existingRecord);
        }
        throw new RuntimeException("Health record not found for user ID: " + userId);
    }

    public HealthRecord getRecordByUserId(Long userId) {
        return healthRecordRepository.findByUser_Id(userId);
    }
}