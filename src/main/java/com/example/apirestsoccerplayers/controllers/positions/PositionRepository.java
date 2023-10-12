package com.example.apirestsoccerplayers.controllers.positions;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position,Integer>{
    Optional<Position> findByName(String positionName);
}
