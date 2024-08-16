package com.cafe.inventory.manager.server.controller;

import com.cafe.inventory.manager.server.entity.User;
import com.cafe.inventory.manager.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to list all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Endpoint to create new user
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        List<String> suggestions = userService.checkUsernameAndSuggest(user.getUsername());
        if (!suggestions.isEmpty()) {
            // Username exists, return suggestions
            return new ResponseEntity<>(suggestions, HttpStatus.CONFLICT);
        }

        // If username is unique, create the user
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Endpoint to delete user based on the ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}