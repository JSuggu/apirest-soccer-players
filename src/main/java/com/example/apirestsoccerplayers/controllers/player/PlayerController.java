package com.example.apirestsoccerplayers.controllers.player;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.apirestsoccerplayers.controllers.league.League;
import com.example.apirestsoccerplayers.handlers.Result;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping("/add")
    public Result addPlayer(@Valid @RequestBody PlayerDTO request)throws NameNotFoundException{
        Player player = playerService.addPlayer(request);
        return new Result(true, 201, "Player added", player);
    }

    @GetMapping("/auth")
    public Result getAllPlayers(){
        List<Player> players = playerService.getAllPlayers();
        return new Result(true, 200, "Success", players);
    }

    @GetMapping("/auth/by-position/{position}")
    public Result getAllPlayersByPosition(@PathVariable(name="position") String positionName){
        List<Player> players = playerService.getPlayersByPosition(positionName);
        return new Result(true, 200, "Success", players);
    }

    @GetMapping("/auth/by-country/{country}")
    public Result getAllPlayersByCountry(@PathVariable(name="country") String countryName){
        List<Player> players = playerService.getPlayersByCountry(countryName);
        return new Result(true, 200, "Success", players);
    }

    @GetMapping("/auth/by-team/{team}")
    public Result getAllPlayersByTeam(@PathVariable(name="team") String teamName){
        List<Player> players = playerService.getPlayersByTeam(teamName);
        return new Result(true, 200, "Success", players);
    }

    @GetMapping("/auth/by-league/{league}")
    public Result getAllPlayersByLeague(@PathVariable(name="league") String leagueName){
        List<Player> players = playerService.getPlayersByLeague(leagueName);
        return new Result(true, 200, "Success", players);
    }

    @PutMapping("/auth/update/{id}")
    public Result updatePlayer(@PathVariable(name="id") Integer playerId, @Valid @RequestBody PlayerDTO request) throws NameNotFoundException, NotFoundException{
        Player playerUpdated = playerService.updatePlayer(playerId, request);
        return new Result(true, 201, "Player updated", playerUpdated);
    }

    @DeleteMapping("/auth/delete/{id}")
    public String deletePlayer(@PathVariable(name="id") Integer playerId){
        return playerService.deletePlayer(playerId);
    }

    //BULK DATA
    @PostMapping(path="/auth/add/many-data")
    public Result addManyPlayers(@RequestParam("file") MultipartFile file) throws IOException{
        List<Player> players = playerService.addManyPlayers(file);
        return new Result(true, 201, "file saved and leagues added", players);
    }
}
