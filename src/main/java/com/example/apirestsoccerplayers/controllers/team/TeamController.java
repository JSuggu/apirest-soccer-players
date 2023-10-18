package com.example.apirestsoccerplayers.controllers.team;

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

import com.example.apirestsoccerplayers.handlers.Result;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/auth/add")
    public Result addTeam(@Valid @RequestBody TeamDTO request) throws NameNotFoundException{
        Team team = teamService.addTeam(request);
        return new Result(true, 201, "Team added", team);
    }

    @GetMapping("")
    public Result getAllTeams(){
        List<Team> teams = teamService.getAllTeams();
        return new Result(true, 200, "response successfully", teams);
    }

    @GetMapping("/{name}")
    public Result getTeam(@PathVariable(name="name") String teamName) throws NameNotFoundException{
        Team team = teamService.getTeam(teamName);
        return new Result(true, 200, "response successfully", team);
    }

    @PutMapping("/auth/update/{id}")
    public Result updateTeam(@PathVariable(name="id") Integer teamId, @Valid @RequestBody TeamDTO request) throws NameNotFoundException, NotFoundException{
        Team team = teamService.updateTeam(teamId, request);
        return new Result(true, 201, "Team updated successfully", team);
    }

    @DeleteMapping("/auth/delete/{id}")
    public String deleteTeam(@PathVariable(name="id") Integer teamId){
        return teamService.deleteTeam(teamId);
    }

    @PostMapping("/auth/add/many-data")
    public Result addManyTeams(@RequestParam("file") MultipartFile file) throws IOException{
        List<Team> teamsOfFile = teamService.addManyTeams(file);
        return new Result(true, 201, "Teams added", teamsOfFile);
    }
}
