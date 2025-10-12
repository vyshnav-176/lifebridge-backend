package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.Doctor;
import com.lifebridge.lifebridge_backend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // Method to get the list of all doctors (for the frontend screen)
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    // Optional: Method to save doctors (for initial setup)
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}
