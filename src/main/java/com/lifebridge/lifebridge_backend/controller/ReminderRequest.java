package com.lifebridge.lifebridgebackend.controller; // Ensure your package name is correct

import java.time.LocalTime;

public class ReminderRequest {
    private Long userId;
    private String medicineName;
    private String dosage;
    private LocalTime reminderTime;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getMedicineName() {
        return medicineName;
    }
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
    public String getDosage() {
        return dosage;
    }
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    public LocalTime getReminderTime() {
        return reminderTime;
    }
    public void setReminderTime(LocalTime reminderTime) {
        this.reminderTime = reminderTime;
    }
}