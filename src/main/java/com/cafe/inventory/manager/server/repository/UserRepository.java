package com.cafe.inventory.manager.server.repository;

import com.cafe.inventory.manager.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
}