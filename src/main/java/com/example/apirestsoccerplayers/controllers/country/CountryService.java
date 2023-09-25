package com.example.apirestsoccerplayers.controllers.country;

import java.util.List;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public Country addCountry(Country request) throws DataIntegrityViolationException{
        Country country = Country.builder()
            .name(request.getName())
            .build();

        return countryRepository.save(country);
    }

    public List<Country> getAllCountries(){
        List<Country> countries = countryRepository.findAll();

        return countries;
    }

    public Country updateCountry(Integer countryId, Country request){
        Optional<Country> dbCountry = countryRepository.findById(countryId);
        if(dbCountry.isPresent()){
            Country newCountry = dbCountry.get();
            newCountry.setName(request.getName());
            return countryRepository.save(newCountry);
        }
        return new Country();
    }

    public String deleteCountry(Integer countryId){
        countryRepository.deleteById(countryId);
        return "Country Deleted";
    }
}
