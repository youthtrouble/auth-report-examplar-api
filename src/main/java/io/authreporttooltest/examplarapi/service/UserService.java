package io.authreporttooltest.examplarapi.service;

import io.authreporttooltest.examplarapi.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();

    public UserService() {
        // Add some dummy data
        users.add(new User(1L, "john", "john@example.com"));
        users.add(new User(2L, "jane", "jane@example.com"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> getUserById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public User createUser(User user) {
        user.setId((long) (users.size() + 1));
        users.add(user);
        return user;
    }
}
