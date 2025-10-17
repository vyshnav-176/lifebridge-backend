package com.lifebridge.lifebridge_backend.controller;

import com.lifebridge.lifebridge_backend.entity.Medicine;
import com.lifebridge.lifebridge_backend.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private static final Long MEDICINE_ADMIN_ID = 7L;

    @Autowired
    private MedicineService medicineService;

    // 1. Get all available medicines (for standard user viewing/ordering)
    @GetMapping
    public ResponseEntity<List<Medicine>> getAllAvailableMedicines() {
        List<Medicine> medicines = medicineService.getAllAvailableMedicines();
        return new ResponseEntity<>(medicines, HttpStatus.OK);
    }

    // 2. Get all medicines (for admin viewing)
    @GetMapping("/admin/all")
    public ResponseEntity<List<Medicine>> getAllMedicinesForAdmin(@RequestParam Long userId) {
        if (!userId.equals(MEDICINE_ADMIN_ID)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Medicine> medicines = medicineService.findAllMedicines();
        return new ResponseEntity<>(medicines, HttpStatus.OK);
    }

    // 3. Add a new medicine (Admin only)
    @PostMapping
    public ResponseEntity<?> addMedicine(@RequestBody Medicine medicine, @RequestParam Long userId) {
        if (!userId.equals(MEDICINE_ADMIN_ID)) {
            return new ResponseEntity<>("Unauthorized. Only Medicine Admin can add medicines.", HttpStatus.FORBIDDEN);
        }
        Medicine savedMedicine = medicineService.saveMedicine(medicine);
        return new ResponseEntity<>(savedMedicine, HttpStatus.CREATED);
    }

    // 4. Delete a medicine (Admin only)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedicine(@PathVariable Long id, @RequestParam Long userId) {
        if (!userId.equals(MEDICINE_ADMIN_ID)) {
            return new ResponseEntity<>("Unauthorized. Only Medicine Admin can delete medicines.", HttpStatus.FORBIDDEN);
        }
        medicineService.deleteMedicine(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 5. Toggle availability (Admin only)
    @PutMapping("/toggle/{id}")
    public ResponseEntity<?> toggleAvailability(@PathVariable Long id, @RequestParam Boolean isAvailable, @RequestParam Long userId) {
        if (!userId.equals(MEDICINE_ADMIN_ID)) {
            return new ResponseEntity<>("Unauthorized. Only Medicine Admin can toggle availability.", HttpStatus.FORBIDDEN);
        }
        return medicineService.toggleAvailability(id, isAvailable)
                .map(updatedMedicine -> new ResponseEntity<>(updatedMedicine, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}