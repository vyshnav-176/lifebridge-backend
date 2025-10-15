package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.Appointment;
import com.lifebridge.lifebridge_backend.entity.User;
import com.lifebridge.lifebridge_backend.repository.AppointmentRepository;
import com.lifebridge.lifebridge_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    public Appointment saveAppointment(Appointment appointment) {
        Optional<User> userOptional = userRepository.findById(appointment.getUser().getId());
        if (userOptional.isPresent()) {
            appointment.setUser(userOptional.get());
            appointment.setStatus("PENDING"); // Default status
            return appointmentRepository.save(appointment);
        }
        throw new RuntimeException("User not found with ID: " + appointment.getUser().getId());
    }

    public List<Appointment> getAppointmentsByUserId(Long userId) {
        return appointmentRepository.findByUser_Id(userId);
    }

    public Appointment updateStatus(Long appointmentId, String newStatus) {
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> {
                    appointment.setStatus(newStatus);
                    return appointmentRepository.save(appointment);
                })
                .orElse(null);
    }
}