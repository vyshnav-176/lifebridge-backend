package com.lifebridge.lifebridge_backend.controller;

import com.lifebridge.lifebridge_backend.entity.Appointment;
import com.lifebridge.lifebridge_backend.entity.User;
import com.lifebridge.lifebridge_backend.service.AppointmentService;
import com.lifebridge.lifebridge_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lifebridge.lifebridge_backend.controller.AppointmentRequest;

import java.util.List;

@RestController
@RequestMapping("/api/appointments") // Base URL: http://localhost:8080/api/appointments
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService; // Needed to fetch the User object

    // In AppointmentController.java

    // Endpoint 1: Book a new appointment (Uses the new DTO)
    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequest request) {

        // 1. Fetch the full User object using the direct userId
        User user = userService.findById(request.getUserId());
        if (user == null) {
            return new ResponseEntity<>("User not found for booking.", HttpStatus.NOT_FOUND);
        }

        // 2. Create the Appointment Entity manually
        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setDoctorName(request.getDoctorName());
        appointment.setSpecialty(request.getSpecialty());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());

        // 3. Save the appointment (status is set to PENDING in AppointmentService)
        Appointment savedAppointment = appointmentService.saveAppointment(appointment);

        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    // Endpoint 2: Get appointment history for a specific user
    // GET from http://localhost:8080/api/appointments/history/2
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Appointment>> getAppointmentHistory(@PathVariable Long userId) {

        List<Appointment> appointments = appointmentService.getAppointmentsByUserId(userId);

        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
    // In AppointmentController.java (Add this endpoint below your existing 'getAppointmentHistory' endpoint)

    // Endpoint 3: Update the status of an appointment (e.g., to CONFIRMED or CANCELLED)
// PUT to http://localhost:8080/api/appointments/status/{appointmentId}?newStatus=CONFIRMED
    @PutMapping("/status/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestParam String newStatus) {

        // Call the service layer to perform the update
        Appointment updatedAppointment = appointmentService.updateStatus(appointmentId, newStatus);

        if (updatedAppointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }
}
