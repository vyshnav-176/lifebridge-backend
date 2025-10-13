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

    public HealthRecord saveOrUpdateRecord(Long userId, HealthRecord record) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            HealthRecord existingRecord = healthRecordRepository.findByUser_Id(user.getId());

            if (existingRecord != null) {
                existingRecord.setBloodGroup(record.getBloodGroup());
                existingRecord.setAllergies(record.getAllergies());
                existingRecord.setMedicalConditions(record.getMedicalConditions());
                existingRecord.setHeight(record.getHeight());
                existingRecord.setWeight(record.getWeight());
                return healthRecordRepository.save(existingRecord);
            } else {
                record.setUser(user);
                return healthRecordRepository.save(record);
            }
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public HealthRecord getRecordByUserId(Long userId) {
        return healthRecordRepository.findByUser_Id(userId);
    }
}