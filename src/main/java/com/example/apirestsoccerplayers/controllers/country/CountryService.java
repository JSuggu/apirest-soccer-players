package com.example.apirestsoccerplayers.controllers.country;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NameNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.apirestsoccerplayers.utils.file_storage.StorageService;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final StorageService storageService;

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

    //BULK DATA
    public List<Country> addManyCountries(MultipartFile file) throws IOException, DataIntegrityViolationException, ConstraintViolationException{
        storageService.saveFile(file);

        List<Country> countriesOfFile = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        Resource resource = storageService.loadFile(fileName);
        File savedFile = resource.getFile();
        BufferedReader br = new BufferedReader(new FileReader(savedFile, StandardCharsets.UTF_8));
        String countryName;

        while((countryName = br.readLine()) != null){
            countriesOfFile.add(Country.builder()
            .name(countryName)
            .build());
        }
        
        br.close();

        return countryRepository.saveAll(countriesOfFile);
    }
}
