package com.example.apirestsoccerplayers.controllers.player;

import java.sql.Date;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.apirestsoccerplayers.controllers.country.CountryService;
import com.example.apirestsoccerplayers.controllers.league.LeagueService;
import com.example.apirestsoccerplayers.controllers.positions.PositionService;
import com.example.apirestsoccerplayers.controllers.team.TeamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final CountryService countryService;
    private final TeamService teamService;
    private final LeagueService leagueService;
    private final PositionService positionService;

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
}
