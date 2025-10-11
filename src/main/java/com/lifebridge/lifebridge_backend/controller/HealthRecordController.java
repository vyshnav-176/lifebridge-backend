package com.lifebridge.lifebridge_backend.controller;

import com.lifebridge.lifebridge_backend.entity.HealthRecord;
import com.lifebridge.lifebridge_backend.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records") // Base URL: http://localhost:8080/api/records
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    // Endpoint 1: Save a new health record
    // POST request to http://localhost:8080/api/records/save
    @PostMapping("/save")
    public ResponseEntity<HealthRecord> saveRecord(@RequestBody HealthRecord record) {

        // This relies on the incoming JSON having the correct user ID link
        HealthRecord savedRecord = healthRecordService.saveRecord(record);

        return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
    }

    // Endpoint 2: Get all records for a specific user
    // GET request to http://localhost:8080/api/records/user/2
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HealthRecord>> getRecordsByUserId(@PathVariable Long userId) {

        List<HealthRecord> records = healthRecordService.getRecordsByUserId(userId);

        if (records.isEmpty()) {
            // Return 404 if no records are found for that user
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(records, HttpStatus.OK);
    }
}
