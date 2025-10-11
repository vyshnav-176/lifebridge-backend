package com.lifebridge.lifebridge_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate; // To store the date of the checkup

@Entity
@Table(name = "health_records")
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link this record to the User table (Many HealthRecords belong to One User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who owns this record

    private String bloodGroup;
    private String conditions; // Chronic conditions or diseases
    private LocalDate lastCheckupDate;

    // Default Constructor
    public HealthRecord() {}

    // Getters and Setters (IntelliJ can generate these, but let's paste them for now)

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

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public LocalDate getLastCheckupDate() {
        return lastCheckupDate;
    }

    public void setLastCheckupDate(LocalDate lastCheckupDate) {
        this.lastCheckupDate = lastCheckupDate;
    }
}