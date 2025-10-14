package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.Reminder;
import com.lifebridge.lifebridge_backend.entity.User;
import com.lifebridge.lifebridge_backend.repository.ReminderRepository;
import com.lifebridge.lifebridge_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;

    public Reminder saveOrUpdateReminder(Reminder reminder) {
        Optional<User> userOptional = userRepository.findById(reminder.getUser().getId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            reminder.setUser(user);
            return reminderRepository.save(reminder);
        }
        throw new RuntimeException("User not found with ID: " + reminder.getUser().getId());
    }

    public List<Reminder> getRemindersByUserId(Long userId) {
        return reminderRepository.findByUser_Id(userId);
    }

    public Reminder toggleReminderStatus(Long reminderId, Boolean isActive) {
        return reminderRepository.findById(reminderId)
                .map(reminder -> {
                    reminder.setIsActive(isActive);
                    return reminderRepository.save(reminder);
                })
                .orElse(null);
    }

    public void deleteReminder(Long id) {
        reminderRepository.deleteById(id);
    }
}