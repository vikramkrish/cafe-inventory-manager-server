package com.cafe.inventory.manager.server.service;

import com.cafe.inventory.manager.server.entity.User;
import com.cafe.inventory.manager.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validateUserCredentials(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }
}
