package com.lifebridge.lifebridge_backend.repository;

import com.lifebridge.lifebridge_backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Custom method to find all appointments associated with a specific user ID
    // Spring Data JPA uses the property name (findByUserId) to generate the SQL query.
    List<Appointment> findByUserId(Long userId);
}
