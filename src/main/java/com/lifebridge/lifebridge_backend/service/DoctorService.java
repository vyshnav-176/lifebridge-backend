package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.Doctor;
import com.lifebridge.lifebridge_backend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public Optional<Doctor> toggleAvailability(Long id, Boolean isAvailable) {
        return doctorRepository.findById(id)
                .map(doctor -> {
                    doctor.setAvailable(isAvailable);
                    return doctorRepository.save(doctor);
                });
    }
}