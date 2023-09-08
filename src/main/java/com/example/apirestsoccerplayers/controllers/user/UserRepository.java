package com.example.apirestsoccerplayers.controllers.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    public Optional<User> findByUsername (String username);
}
