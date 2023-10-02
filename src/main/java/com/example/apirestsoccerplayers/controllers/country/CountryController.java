package com.example.apirestsoccerplayers.controllers.country;

import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apirestsoccerplayers.handlers.Result;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @PostMapping(path="/add")
    public Result addCountry(@Valid @RequestBody Country request){
        Country country = countryService.addCountry(request);
        return new Result(true, 201, "country saved", country);
    }

    @GetMapping(path="/auth")
    public Result getAllCountries(){
        List<Country> countries = countryService.getAllCountries();
        return new Result(true, 200, "response successfully", countries);
    }

    @GetMapping(path="/auth/{name}")
    public Result getCountry(@PathVariable(name="name") String countryName) throws NameNotFoundException{
        Country country = countryService.getCountry(countryName);
        return new Result(true, 200, "response successfully", country);
    }

    @PutMapping(path="/auth/update/{id}")
    public Result updateCountry(@PathVariable(name="id") Integer countryId, @Valid @RequestBody Country request) throws NotFoundException{
        Country country = countryService.updateCountry(countryId, request);
        return new Result(true, 201, "country updated", country);
    }

    @DeleteMapping(path="/auth/delete/{id}")
    public String deleteCountry(@PathVariable(name="id") Integer countryId){
        return countryService.deleteCountry(countryId);
    }
}
