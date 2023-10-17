package com.example.apirestsoccerplayers.controllers.league;

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

import com.example.apirestsoccerplayers.controllers.country.Country;
import com.example.apirestsoccerplayers.handlers.Result;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/leagues")
@RequiredArgsConstructor
public class LeagueController {
    private final LeagueService leagueService;

    @PostMapping("/add")
    public Result addLeague(@Valid @RequestBody LeagueDTO request) throws NameNotFoundException{
        League league = leagueService.addleague(request);
        return new Result(true, 201, "Country added", league);
    }

    @GetMapping(path="/auth")
    public Result getAllLeagues(){
        List<League> leagues = leagueService.getAllLeagues();
        return new Result(true, 200, "response successfully", leagues);
    }

    @GetMapping(path="/auth/{name}")
    public Result getLeague(@PathVariable(name="name") String leagueName) throws NameNotFoundException{
        League league = leagueService.getLeague(leagueName);
        return new Result(true, 200, "response successfully", league);
    }

    @PutMapping(path="/auth/update/{id}")
    public Result updateLeague(@PathVariable(name="id") Integer countryId, @Valid @RequestBody LeagueDTO request) throws NotFoundException, NameNotFoundException{
        League country = leagueService.updateLeague(countryId, request);
        if(country.getName() == null) return new Result(false, 400, "user not found");
        return new Result(true, 201, "country updated", country);
    }

    @DeleteMapping(path="/auth/delete/{id}")
    public String deleteLeague(@PathVariable(name="id") Integer countryId){
        return leagueService.deleteLeague(countryId);
    }

    //BULK DATA
    @PostMapping(path="/auth/add/many-data")
    public Result addManyLeagues(@RequestParam("file") MultipartFile file) throws IOException{
        List<League> leagues = leagueService.addManyLeagues(file);
        return new Result(true, 201, "file saved and leagues added", leagues);
    }
}
