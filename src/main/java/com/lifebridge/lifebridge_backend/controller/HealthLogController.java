package com.lifebridge.lifebridge_backend.controller;

import com.lifebridge.lifebridge_backend.entity.HealthLog;
import com.lifebridge.lifebridge_backend.service.HealthLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-logs")
public class HealthLogController {

    @Autowired
    private HealthLogService healthLogService;

    @PostMapping("/save")
    public ResponseEntity<HealthLog> saveHealthLog(@RequestBody HealthLog healthLog) {
        HealthLog savedLog = healthLogService.saveHealthLog(healthLog);
        return new ResponseEntity<>(savedLog, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HealthLog>> getHealthLogsByUserId(@PathVariable Long userId) {
        List<HealthLog> logs = healthLogService.getHealthLogsByUserId(userId);
        if (logs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}