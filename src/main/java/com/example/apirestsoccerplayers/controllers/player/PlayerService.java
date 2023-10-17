package com.example.apirestsoccerplayers.controllers.player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
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
import com.example.apirestsoccerplayers.controllers.league.League;
import com.example.apirestsoccerplayers.controllers.league.LeagueService;
import com.example.apirestsoccerplayers.controllers.positions.Position;
import com.example.apirestsoccerplayers.controllers.positions.PositionService;
import com.example.apirestsoccerplayers.controllers.team.Team;
import com.example.apirestsoccerplayers.controllers.team.TeamService;
import com.example.apirestsoccerplayers.utils.file_storage.StorageService;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final CountryService countryService;
    private final TeamService teamService;
    private final LeagueService leagueService;
    private final PositionService positionService;
    private final StorageService storageService;

    public Player addPlayer(PlayerDTO request) throws DataIntegrityViolationException, NameNotFoundException{
        Player player = Player.builder()
            .name(request.getName())
            .country(countryService.getCountry(request.getCountryName()))
            .team(teamService.getTeam(request.getTeamName()))
            .league(leagueService.getLeague(request.getLeagueName()))
            .birthday(Date.valueOf(request.getBirthday()))
            .position(positionService.getPosition(request.getPositionsName()))
            .build();
        
        return playerRepository.save(player);
    }

    public List<Player> getAllPlayers(){
        List<Player> players = playerRepository.findAll();
        return players;
    }

    public List<Player> getPlayersByPosition(String positionName){
        List<Player> players = playerRepository.findPlayersByPositionName(positionName);
        return players;
    }

    public List<Player> getPlayersByCountry(String countryName){
        List<Player> players = playerRepository.findPlayersByCountryName(countryName);
        return players;
    }

    public List<Player> getPlayersByTeam(String teamName){
        List<Player> players = playerRepository.findPlayersByTeamName(teamName);
        return players;
    }

    public List<Player> getPlayersByLeague(String leagueName){
        List<Player> players = playerRepository.findPlayersByLeagueName(leagueName);
        return players;
    }

    public Player updatePlayer(Integer playerId, PlayerDTO request) throws DataIntegrityViolationException, NameNotFoundException, NotFoundException{
        Player playerDb = playerRepository.findById(playerId).orElseThrow(() -> new NotFoundException());

        playerDb.setName(request.getName());
        playerDb.setCountry(countryService.getCountry(request.getCountryName()));
        playerDb.setTeam(teamService.getTeam(request.getTeamName()));
        playerDb.setLeague(leagueService.getLeague(request.getLeagueName()));
        playerDb.setPosition(positionService.getPosition(request.getPositionsName()));

        return playerDb;
    }

    public String deletePlayer(Integer playerId){
        playerRepository.deleteById(playerId);
        return "Player deleted";
    }

    //BULK DATA
    public List<Player> addManyPlayers(MultipartFile file) throws IOException, DataIntegrityViolationException, ConstraintViolationException{
        storageService.saveFile(file);

        Map<String, Country> countriesFromDb = new HashMap<>();
        Map<String, Team> teamsFromDb = new HashMap<>();
        Map<String, League> leaguesFromDb = new HashMap<>();
        Map<String, Position> positionsFromDb = new HashMap<>();
        countryService.getAllCountries().forEach(country -> countriesFromDb.put(country.getName(), country));
        teamService.getAllTeams().forEach(team -> teamsFromDb.put(team.getName(), team));
        leagueService.getAllLeagues().forEach(league -> leaguesFromDb.put(league.getName(), league));
        positionService.getAllPositions().forEach(position -> positionsFromDb.put(position.getName(), position));

        List<Player> playersOfFile = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        Resource resource = storageService.loadFile(fileName);
        File savedFile = resource.getFile();
        BufferedReader br = new BufferedReader(new FileReader(savedFile, StandardCharsets.UTF_8));
        String line, playerName, teamName, leagueName, birthday, countryName, positionName;
        String[] arrayLine; //playerName,teamName,leagueName,birthday,countryName,positionName

        while((line = br.readLine()) != null){
            arrayLine = line.split(",");
            playerName = arrayLine[0];
            teamName = arrayLine[1];
            leagueName = arrayLine[2];
            birthday = arrayLine[3];
            countryName = arrayLine[4];
            positionName = arrayLine[5];

            playersOfFile.add(Player.builder()
            .name(playerName)
            .team(teamsFromDb.get(teamName))
            .league(leaguesFromDb.get(leagueName))
            .birthday(Date.valueOf(birthday))
            .country(countriesFromDb.get(countryName))
            .position(positionsFromDb.get(positionName))
            .build());
        }

        br.close();

        return playerRepository.saveAll(playersOfFile);
    }
}
