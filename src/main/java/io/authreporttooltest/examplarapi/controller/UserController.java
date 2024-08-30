package io.authreporttooltest.examplarapi.controller;

import io.authreporttooltest.examplarapi.model.User;
import io.authreporttooltest.examplarapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing User-related operations.
 * This controller uses Spring Security's @PreAuthorize annotations for role-based access control.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Inject UserService for handling business logic
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Retrieve all users. Only accessible to users with ROLE_ADMIN.
     *
     * @return A list of all users.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        logger.debug("Attempting to get all users");
        return userService.getAllUsers();
    }

    /**
     * Retrieve a specific user by ID. Accessible to users with ROLE_ADMIN or ROLE_USER.
     *
     * @param id The ID of the user to retrieve.
     * @return The requested User object.
     * @throws RuntimeException if the requested user is not found.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Create a new user. Only accessible to users with ROLE_ADMIN.
     *
     * @param user The User object to be created, passed in the request body.
     * @return The newly created User object.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}