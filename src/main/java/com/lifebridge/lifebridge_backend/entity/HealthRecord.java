package com.lifebridge.lifebridge_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate; // To store the date of the checkup

@Entity
@Table(name = "health_records")
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // In HealthRecord.java

// Link this record to the User table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // <--- ADD THIS LINE TO BREAK THE INFINITE LOOP
    private User user;

    private String bloodGroup;
    private String allergies;       // <--- NEW FIELD
    private String chronicDiseases; // <--- RENAMED FIELD (was 'conditions')
    private Double heightCm;        // <--- NEW FIELD
    private Double weightKg;        // <--- NEW FIELD
    private Double bmiScore;        // <--- NEW FIELD
    private LocalDate lastCheckupDate;

    // Default Constructor
    public HealthRecord() {}

    // Getters and Setters (Updated for all fields)

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

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getChronicDiseases() {
        return chronicDiseases;
    }

    public void setChronicDiseases(String chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public Double getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(Double heightCm) {
        this.heightCm = heightCm;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public Double getBmiScore() {
        return bmiScore;
    }

    public void setBmiScore(Double bmiScore) {
        this.bmiScore = bmiScore;
    }

    public LocalDate getLastCheckupDate() {
        return lastCheckupDate;
    }

    public void setLastCheckupDate(LocalDate lastCheckupDate) {
        this.lastCheckupDate = lastCheckupDate;
    }
}