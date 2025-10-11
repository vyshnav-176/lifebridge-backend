package com.lifebridge.lifebridge_backend.controller;

import com.lifebridge.lifebridge_backend.entity.Reminder;
import com.lifebridge.lifebridge_backend.entity.User; // ADDED
import com.lifebridge.lifebridge_backend.service.ReminderService;
import com.lifebridge.lifebridgebackend.controller.ReminderRequest; // Import the DTO
import com.lifebridge.lifebridge_backend.service.UserService; // ADDED
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.lifebridge.lifebridgebackend.controller.ReminderRequest; // ADDED

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private UserService userService; // Now correctly found

    // Endpoint 1: Save a new reminder
    @PostMapping("/save")
    public ResponseEntity<Reminder> saveReminder(@RequestBody ReminderRequest request) {

        // 1. Get the User object from the database using the ID from the request
        User user = userService.findById(request.getUserId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // User not found
        }

        // 2. Create the Reminder Entity manually
        Reminder reminder = new Reminder();
        reminder.setUser(user); // Link the User object
        reminder.setMedicineName(request.getMedicineName());
        reminder.setDosage(request.getDosage());
        reminder.setReminderTime(request.getReminderTime());

        // 3. Save the record
        Reminder savedReminder = reminderService.saveReminder(reminder);

        return new ResponseEntity<>(savedReminder, HttpStatus.CREATED);
    }

    // Endpoint 2: Get all reminders for a specific user (Needs to be re-added below)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reminder>> getRemindersByUserId(@PathVariable Long userId) {

        List<Reminder> reminders = reminderService.getRemindersByUserId(userId);

        if (reminders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reminders, HttpStatus.OK);
    }

    // Endpoint 3: Toggle the active status of a reminder (Needs to be re-added below)
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
}