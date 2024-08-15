package com.cafe.inventory.manager.server.repository;

import com.cafe.inventory.manager.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom queries here if needed
}