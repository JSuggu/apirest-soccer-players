package com.example.apirestsoccerplayers.controllers.league;

import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.apirestsoccerplayers.controllers.country.CountryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeagueService {
    @Autowired
    private final LeagueRepository leagueRepository;
    private final CountryService countryService;

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
}
