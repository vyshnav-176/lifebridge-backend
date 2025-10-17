package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.Medicine;
import com.lifebridge.lifebridge_backend.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicine> getAllAvailableMedicines() {
        return medicineRepository.findByIsAvailableTrue();
    }

    public List<Medicine> findAllMedicines() {
        return medicineRepository.findAll();
    }

    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }

    public Optional<Medicine> toggleAvailability(Long id, Boolean isAvailable) {
        return medicineRepository.findById(id)
                .map(medicine -> {
                    medicine.setIsAvailable(isAvailable);
                    return medicineRepository.save(medicine);
                });
    }

    public Optional<Medicine> findById(Long id) {
        return medicineRepository.findById(id);
    }
}