package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.Appointment;
import com.lifebridge.lifebridge_backend.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Method 1: Save a new appointment (Book)
    public Appointment saveAppointment(Appointment appointment) {
        // When a new appointment is saved, ensure the status is set to PENDING
        appointment.setStatus("PENDING");
        return appointmentRepository.save(appointment);
    }

    // Method 2: Get all appointments for a specific user (History)
    public List<Appointment> getAppointmentsByUserId(Long userId) {
        // Uses the custom findByUserId method from the Repository
        return appointmentRepository.findByUserId(userId);
    }
    // In AppointmentService.java

    // New Method 3: Update the status of an existing appointment
    public Appointment updateStatus(Long appointmentId, String newStatus) {
        // Find the appointment by its ID
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> {
                    // If found, update the status field
                    appointment.setStatus(newStatus);
                    // Save the updated appointment back to the database
                    return appointmentRepository.save(appointment);
                })
                .orElse(null); // Return null if the appointment ID is not found
    }
}
