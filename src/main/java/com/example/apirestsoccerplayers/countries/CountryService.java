package com.example.apirestsoccerplayers.countries;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public String addCountry(Country request){
        Country country = Country.builder()
            .name(request.getName())
            .build();

        try {
            countryRepository.save(country);
        } catch (TransactionSystemException e){
            return "Country can't be added because can only contain words";
        }

        return "Country added successfully";
    }

    public List<Country> getAllCountries(){
        List<Country> countries = countryRepository.findAll();

        return countries;
    }

    public String updateCountry(Integer countryId, Country request){
        Optional<Country> dbCountry = countryRepository.findById(countryId);
        if(dbCountry.isPresent()){
            Country newCountry = dbCountry.get();
            newCountry.setName(request.getName());
            countryRepository.save(newCountry);
            return "The country was updated successfully";
        }
        return "The country that tries modified don't exist";
    }

    public String deleteCountry(Integer countryId){
        countryRepository.deleteById(countryId);
        return "Country Deleted";
    }
}
