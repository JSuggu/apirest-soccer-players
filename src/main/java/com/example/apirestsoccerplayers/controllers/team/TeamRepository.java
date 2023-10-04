package com.example.apirestsoccerplayers.controllers.team;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer>{
    public Optional<Team> findByName(String teamName);
}
