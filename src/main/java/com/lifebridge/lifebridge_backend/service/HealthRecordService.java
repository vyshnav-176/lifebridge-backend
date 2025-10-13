package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.HealthRecord;
import com.lifebridge.lifebridge_backend.entity.User;
import com.lifebridge.lifebridge_backend.repository.HealthRecordRepository;
import com.lifebridge.lifebridge_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HealthRecordService {

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    @Autowired
    private UserRepository userRepository;

    public HealthRecord saveRecord(HealthRecord record) {
        Optional<User> userOptional = userRepository.findById(record.getUser().getId());

        if (userOptional.isPresent()) {
            record.setUser(userOptional.get());
            return healthRecordRepository.save(record);
        } else {
            throw new RuntimeException("User not found with ID: " + record.getUser().getId());
        }
    }

    public List<HealthRecord> getRecordsByUserId(Long userId) {
        return healthRecordRepository.findByUser_Id(userId);
    }
}