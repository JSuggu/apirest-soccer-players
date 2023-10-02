package com.example.apirestsoccerplayers.controllers.league;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Integer>{
    public Optional<League> findByName(String leagueName);
}
