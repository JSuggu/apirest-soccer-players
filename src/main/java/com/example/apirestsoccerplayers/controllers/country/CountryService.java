package com.example.apirestsoccerplayers.controllers.country;

import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

    public Country getCountry(String name) throws NameNotFoundException{
        return countryRepository.findByName(name).orElseThrow(()-> new NameNotFoundException("Country name not found"));
    }

    public List<Country> getAllCountries(){
        List<Country> countries = countryRepository.findAll();

        return countries;
    }

    public Country updateCountry(Integer countryId, Country request) throws DataIntegrityViolationException, NotFoundException{
        Country dbCountry = countryRepository.findById(countryId).orElseThrow(()-> new NotFoundException());
        dbCountry.setName(request.getName());
        
        return countryRepository.save(dbCountry);
    }

    public String deleteCountry(Integer countryId){
        countryRepository.deleteById(countryId);
        return "Country Deleted";
    }

    
}
