package com.lifebridge.lifebridge_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // Needed for the User relationship
import jakarta.persistence.*;
import java.time.LocalTime; // To store the time of the reminder

@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link this record to the User table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // Breaks the loop, same as HealthRecord
    private User user;

    private String medicineName;
    private String dosage; // e.g., "1 tablet", "5 ml"
    private LocalTime reminderTime; // The time the reminder should fire
    private Boolean isActive = true; // Flag to enable/disable the reminder

    // Default Constructor
    public Reminder() {}

    // Getters and Setters (IntelliJ can generate these, but paste for now)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}