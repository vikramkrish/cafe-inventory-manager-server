package com.cafe.inventory.manager.server.service;

import com.cafe.inventory.manager.server.entity.User;
import com.cafe.inventory.manager.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        List<String> suggestions = checkUsernameAndSuggest(user.getUsername());
        if(!suggestions.isEmpty()) {
            return null; // User already exists
        }
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Method to find the user based on the username
    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    // Method to check if the username exists and suggest alternatives
    public List<String> checkUsernameAndSuggest(String username) {
        List<String> suggestions = new ArrayList<>();
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            suggestions.add(username + "1");
            suggestions.add(username + "123");
            suggestions.add(username + new Random().nextInt(100));
            return suggestions;
        }
        return suggestions;
    }

    public boolean deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return userRepository.findById(userId).isEmpty();
    }
}
