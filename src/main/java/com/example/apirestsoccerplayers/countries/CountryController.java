package com.example.apirestsoccerplayers.countries;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @PostMapping(path="/add")
    public String addCountry(@RequestBody Country request){
        return countryService.addCountry(request);
    }

    @GetMapping(path="")
    public List<Country> getAllCountries(){
        return countryService.getAllCountries();
    }

    @PutMapping(path="/update/{id}")
    public String updateCountry(@PathVariable(name="id") Integer countryId, @RequestBody Country request){
        return countryService.updateCountry(countryId, request);
    }

    @DeleteMapping(path="/delete/{id}")
    public String deleteCountry(@PathVariable(name="id") Integer countryId){
        return countryService.deleteCountry(countryId);
    }
}
