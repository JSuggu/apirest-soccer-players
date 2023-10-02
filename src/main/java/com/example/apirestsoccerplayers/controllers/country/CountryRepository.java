package com.example.apirestsoccerplayers.controllers.country;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    public Optional<Country> findByName(String name);
}
