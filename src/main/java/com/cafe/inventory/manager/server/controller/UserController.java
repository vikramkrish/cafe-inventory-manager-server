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
    public ResponseEntity<List<User>> getAUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    // Endpoint to find the user based on the given username
    @GetMapping("/{username}")
    public ResponseEntity<Object> findUserByUserName(@PathVariable String username) {
        User user = userService.findUserByUserName(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Details not found for the given username: [ " + username + " ]", HttpStatus.NOT_FOUND);
        }

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
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        if (userService.deleteUser(username)) {
            return new ResponseEntity<>("User Deleted Successfully for the given id: [ " + username + " ]", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete the user for the given id: [ " + username + " ]", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}