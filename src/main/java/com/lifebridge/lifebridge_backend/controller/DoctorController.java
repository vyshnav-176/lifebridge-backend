package com.lifebridge.lifebridge_backend.controller;

import com.lifebridge.lifebridge_backend.entity.Doctor;
import com.lifebridge.lifebridge_backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    private static final Long HOSPITAL_ADMIN_ID = 5L;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.findAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor, @RequestParam Long userId) {
        if (!userId.equals(HOSPITAL_ADMIN_ID)) {
            return new ResponseEntity<>("Unauthorized. Only Hospital Admin can add doctors.", HttpStatus.FORBIDDEN);
        }
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id, @RequestParam Long userId) {
        if (!userId.equals(HOSPITAL_ADMIN_ID)) {
            return new ResponseEntity<>("Unauthorized. Only Hospital Admin can delete doctors.", HttpStatus.FORBIDDEN);
        }
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/toggle/{id}")
    public ResponseEntity<?> toggleAvailability(@PathVariable Long id, @RequestParam Boolean isAvailable, @RequestParam Long userId) {
        if (!userId.equals(HOSPITAL_ADMIN_ID)) {
            return new ResponseEntity<>("Unauthorized. Only Hospital Admin can toggle availability.", HttpStatus.FORBIDDEN);
        }
        return doctorService.toggleAvailability(id, isAvailable)
                .map(updatedDoctor -> new ResponseEntity<>(updatedDoctor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}