package com.lifebridge.lifebridge_backend.controller;

import com.lifebridge.lifebridge_backend.entity.User;
import com.lifebridge.lifebridge_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users") // Base URL: http://localhost:8080/api/users
public class UserController {

    @Autowired
    private UserService userService; // Inject the Service layer

    // Handles POST requests to /api/users/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        // Call the service layer to execute registration and business logic checks
        User registeredUser = userService.registerUser(user);

        // Check the result from the service
        if (registeredUser == null) {
            // If the service returns null, email was already taken (Conflict: 409)
            return new ResponseEntity<>("Error: Email already in use.", HttpStatus.CONFLICT);
        }

        // If successful, return the user data and an HTTP 201 Created status
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
    // In UserController.java

    // The API endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {

        // Authenticate the user using the Service layer
        User user = userService.authenticateUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (user == null) {
            // Failure: HTTP 401 Unauthorized (standard response for failed login)
            return new ResponseEntity<>("Invalid credentials.", HttpStatus.UNAUTHORIZED);
        }

        // Success: Return user data (without the password hash)
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
