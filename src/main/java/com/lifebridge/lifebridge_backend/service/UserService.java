package com.lifebridge.lifebridge_backend.service;

import com.lifebridge.lifebridge_backend.entity.User;
import com.lifebridge.lifebridge_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- This line is new!

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired // <--- Injecting the encoder you configured in SecurityConfig
    private PasswordEncoder passwordEncoder;

    // The method to register a new user
    public User registerUser(User user) {

        // 1. Business Logic: Check if user already exists by email
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return null;
        }

        // 2. CRITICAL SECURITY STEP: Encrypt the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); // Set the HASHED password back on the user object

        // 3. Save the new user to the database
        return userRepository.save(user);
    }

    // New method for user login authentication
    public User authenticateUser(String email, String password) {

        // 1. Find the user by email using the Repository
        User user = userRepository.findByEmail(email);

        // 2. Check if user exists AND if the submitted password matches the stored hash
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Success: passwords match
            return user;
        }

        // Failure: user not found or password doesn't match
        return null;
    }
    // In UserService.java (add this new method)

    public User findById(Long userId) {
        // Uses the default findById method from JpaRepository (inherited by UserRepository)
        return userRepository.findById(userId).orElse(null);
    }
}