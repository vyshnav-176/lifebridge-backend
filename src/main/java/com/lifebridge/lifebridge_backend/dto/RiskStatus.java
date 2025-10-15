package com.lifebridge.lifebridge_backend.dto;

public class RiskStatus {
    private String status;
    private String message;
    private String color;

    public RiskStatus(String status, String message, String color) {
        this.status = status;
        this.message = message;
        this.color = color;
    }

    // Getters and Setters (Omitted for brevity, but you must include them in the file)
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
