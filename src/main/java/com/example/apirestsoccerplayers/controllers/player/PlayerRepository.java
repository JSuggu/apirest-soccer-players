package com.example.apirestsoccerplayers.controllers.player;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer>{
    public List<Player> findPlayersByPositionsName(String positionName);
    public List<Player> findPlayersByCountryName(String positionName);
    public List<Player> findPlayersByTeamName(String positionName);
    public List<Player> findPlayersByLeagueName(String positionName);
}
