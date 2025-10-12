package com.lifebridge.lifebridge_backend.repository;

import com.lifebridge.lifebridge_backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // We will use the findAll() method inherited from JpaRepository to get the list
}