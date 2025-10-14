package com.lifebridge.lifebridge_backend.repository;

import com.lifebridge.lifebridge_backend.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUser_Id(Long userId);
}