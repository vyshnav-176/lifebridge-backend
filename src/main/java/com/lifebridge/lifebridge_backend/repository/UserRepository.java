package com.lifebridge.lifebridge_backend.repository;

import com.lifebridge.lifebridge_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom method to check for existing email
    User findByEmail(String email);
}