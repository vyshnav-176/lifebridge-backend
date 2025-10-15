package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.HealthLog;
import com.lifebridge.lifebridge_backend.entity.User;
import com.lifebridge.lifebridge_backend.repository.HealthLogRepository;
import com.lifebridge.lifebridge_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HealthLogService {

    @Autowired
    private HealthLogRepository healthLogRepository;

    @Autowired
    private UserRepository userRepository;

    public HealthLog saveHealthLog(HealthLog healthLog) {
        Optional<User> userOptional = userRepository.findById(healthLog.getUser().getId());

        if (userOptional.isPresent()) {
            healthLog.setUser(userOptional.get());
            healthLog.setTimestamp(LocalDateTime.now());
            return healthLogRepository.save(healthLog);
        }
        throw new RuntimeException("User not found with ID: " + healthLog.getUser().getId());
    }

    public List<HealthLog> getHealthLogsByUserId(Long userId) {
        // Now returns logs sorted by timestamp descending
        return healthLogRepository.findByUser_IdOrderByTimestampDesc(userId);
    }
}
