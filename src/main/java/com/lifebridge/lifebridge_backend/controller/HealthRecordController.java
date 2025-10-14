package com.lifebridge.lifebridge_backend.controller;

import com.lifebridge.lifebridge_backend.entity.HealthRecord;
import com.lifebridge.lifebridge_backend.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    // Endpoint 1: Save a new health record (POST)
    @PostMapping("/save/{userId}")
    public ResponseEntity<HealthRecord> saveRecord(@PathVariable Long userId, @RequestBody HealthRecord record) {
        HealthRecord savedRecord = healthRecordService.saveRecord(userId, record);
        return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
    }

    // Endpoint 2: Get a single record for a specific user (GET)
    @GetMapping("/user/{userId}")
    public ResponseEntity<HealthRecord> getRecordByUserId(@PathVariable Long userId) {
        HealthRecord record = healthRecordService.getRecordByUserId(userId);
        if (record == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    // NEW: Endpoint 3 to handle PUT requests for updating a record
    @PutMapping("/user/{userId}")
    public ResponseEntity<HealthRecord> updateRecord(@PathVariable Long userId, @RequestBody HealthRecord record) {
        HealthRecord updatedRecord = healthRecordService.updateRecord(userId, record);
        return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
    }
}