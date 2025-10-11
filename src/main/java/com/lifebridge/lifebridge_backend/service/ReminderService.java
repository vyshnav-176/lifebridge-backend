package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.Reminder;
import com.lifebridge.lifebridge_backend.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    // Method 1: Save a new medicine reminder
    public Reminder saveReminder(Reminder reminder) {
        // Saves the new reminder to the database
        return reminderRepository.save(reminder);
    }

    // Method 2: Get all reminders for a specific user
    public List<Reminder> getRemindersByUserId(Long userId) {
        // Uses the custom findByUserId method from the Repository
        return reminderRepository.findByUserId(userId);
    }

    // Method 3 (Optional but good for a mobile app): Toggle reminder status
    public Reminder toggleReminderStatus(Long reminderId, Boolean isActive) {
        // Find the existing reminder by its ID
        return reminderRepository.findById(reminderId)
                .map(reminder -> {
                    // If found, update the isActive status
                    reminder.setIsActive(isActive);
                    // Save the updated reminder back to the database
                    return reminderRepository.save(reminder);
                })
                .orElse(null); // Return null if the reminder ID is not found
    }
}