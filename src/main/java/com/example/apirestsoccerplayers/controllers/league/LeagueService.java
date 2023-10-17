package com.example.apirestsoccerplayers.controllers.league;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NameNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.apirestsoccerplayers.controllers.country.Country;
import com.example.apirestsoccerplayers.controllers.country.CountryService;
import com.example.apirestsoccerplayers.utils.file_storage.StorageService;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final CountryService countryService;
    private final StorageService storageService;

    public League addleague(LeagueDTO request) throws DataIntegrityViolationException, NameNotFoundException{
        League league = League.builder()
            .name(request.getName())
            .country(countryService.getCountry(request.getCountryName()))
            .build();

        return leagueRepository.save(league);
    }

    public League getLeague(String leagueName) throws NameNotFoundException{
        League league = leagueRepository.findByName(leagueName).orElseThrow(()-> new NameNotFoundException("League not found"));
        return league;
    }

    public List<League> getAllLeagues(){
        List<League> leagues = leagueRepository.findAll();
        return leagues;
    }

    public League updateLeague(Integer leagueId, LeagueDTO request) throws DataIntegrityViolationException, NotFoundException, NameNotFoundException{
        League dbLeague = leagueRepository.findById(leagueId).orElseThrow(()-> new NotFoundException());
        dbLeague.setName(request.getName());
        dbLeague.setCountry(countryService.getCountry(request.getCountryName()));

        return leagueRepository.save(dbLeague);
    }

    public String deleteLeague(Integer leagueId){
        leagueRepository.deleteById(leagueId);
        return "League deleted";
    }

    //BULK DATA
    public List<League> addManyLeagues(MultipartFile file) throws IOException, DataIntegrityViolationException, ConstraintViolationException{
        storageService.saveFile(file);

        Map<String, Country> countriesFromDb = new HashMap<>();
        countryService.getAllCountries().forEach(country -> countriesFromDb.put(country.getName(), country));;

        List<League> leaguesOfFile = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        Resource resource = storageService.loadFile(fileName);
        File savedFile = resource.getFile();
        BufferedReader br = new BufferedReader(new FileReader(savedFile, StandardCharsets.UTF_8));
        String line, leagueName, countryName;
        String[] arrayLine;

        while((line = br.readLine()) != null){
            arrayLine = line.split(",");
            leagueName = arrayLine[0];
            countryName = arrayLine[1];

            leaguesOfFile.add(League.builder()
            .name(leagueName)
            .country(countriesFromDb.get(countryName))
            .build());
        }

        br.close();

        return leagueRepository.saveAll(leaguesOfFile);
    }
}
