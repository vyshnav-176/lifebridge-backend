package com.lifebridge.lifebridge_backend.repository;

import com.lifebridge.lifebridge_backend.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByIsAvailableTrue();
}