package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.dto.RiskStatus;
import com.lifebridge.lifebridge_backend.entity.HealthLog;
import com.lifebridge.lifebridge_backend.entity.User;
import com.lifebridge.lifebridge_backend.repository.HealthLogRepository;
import com.lifebridge.lifebridge_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            // Timestamp is set in the controller/logic layer
            return healthLogRepository.save(healthLog);
        }
        throw new RuntimeException("User not found with ID: " + healthLog.getUser().getId());
    }

    public List<HealthLog> getHealthLogsByUserId(Long userId) {
        return healthLogRepository.findByUser_IdOrderByTimestampDesc(userId);
    }

    // CORRECTED METHOD: ASSESS HEALTH RISK
    public RiskStatus assessHealthRisk(Long userId) {
        List<HealthLog> logs = getHealthLogsByUserId(userId);

        // 1. Check if any data exists
        if (logs.isEmpty()) {
            return new RiskStatus("NO_DATA", "Log your first vital reading!", "#B0B0B0");
        }

        HealthLog latestLog = logs.get(0);

        // --- Custom Logic Variables ---
        String status = "NORMAL";
        String message = "Vitals look good!";
        String color = "#34C759"; // Green
        StringBuilder alertMessage = new StringBuilder();

        // 2. Blood Pressure Assessment
        boolean isBPCriticallyHigh = latestLog.getSystolic() >= 140 || latestLog.getDiastolic() >= 90;
        boolean isBPElevated = latestLog.getSystolic() >= 130 || latestLog.getDiastolic() >= 80;

        if (isBPCriticallyHigh) {
            status = "HIGH_RISK";
            alertMessage.append("BP is critically high. ");
        } else if (isBPElevated) {
            if (!status.equals("HIGH_RISK")) {
                status = "ELEVATED";
                alertMessage.append("BP is elevated. ");
            }
        }

        // 3. Glucose Assessment
        boolean isGlucoseHigh = latestLog.getGlucose() >= 126;

        if (isGlucoseHigh) {
            if (!status.equals("HIGH_RISK")) {
                status = "HIGH_RISK";
            }
            alertMessage.append("Glucose is high. ");
        }

        // 4. Determine final message and color based on the highest risk level
        if (status.equals("HIGH_RISK")) {
            color = "#FF4D4D"; // Red
            message = alertMessage.toString().trim() + " Seek immediate medical attention.";
        } else if (status.equals("ELEVATED")) {
            color = "#FFD700"; // Yellow
            message = alertMessage.toString().trim() + " Monitor closely.";
        }

        // 5. CRUCIAL FIX: If no alerts were appended, explicitly return the clean NORMAL status.
        if (alertMessage.length() == 0) {
            return new RiskStatus("NORMAL", "Vitals look good!", "#34C759");
        }

        return new RiskStatus(status, message, color);
    }
}
