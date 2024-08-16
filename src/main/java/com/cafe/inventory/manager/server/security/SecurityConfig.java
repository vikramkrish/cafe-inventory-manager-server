package com.cafe.inventory.manager.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users").permitAll()  // Allow access to "users" endpoint
                        .requestMatchers("/login").permitAll()  // Allow access to "login" endpoint
                        .anyRequest().authenticated()  // All other endpoints require authentication
                )
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection (for testing, enable in production)
                .httpBasic(withDefaults());  // Use basic authentication

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // For storing the encoded password
    }
}
