package com.lifebridge.lifebridge_backend.controller;

import com.lifebridge.lifebridge_backend.entity.Reminder;
import com.lifebridge.lifebridge_backend.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping("/save")
    public ResponseEntity<Reminder> saveReminder(@RequestBody Reminder reminder) {
        Reminder savedReminder = reminderService.saveOrUpdateReminder(reminder);
        return new ResponseEntity<>(savedReminder, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reminder>> getRemindersByUserId(@PathVariable Long userId) {
        List<Reminder> reminders = reminderService.getRemindersByUserId(userId);
        if (reminders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reminders, HttpStatus.OK);
    }

    @PutMapping("/toggle/{reminderId}")
    public ResponseEntity<Reminder> toggleReminderStatus(
            @PathVariable Long reminderId,
            @RequestParam Boolean active) {

        Reminder updatedReminder = reminderService.toggleReminderStatus(reminderId, active);

        if (updatedReminder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedReminder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}