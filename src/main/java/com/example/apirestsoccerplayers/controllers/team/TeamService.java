package com.example.apirestsoccerplayers.controllers.team;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
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

import com.example.apirestsoccerplayers.controllers.league.League;
import com.example.apirestsoccerplayers.controllers.league.LeagueService;
import com.example.apirestsoccerplayers.utils.file_storage.StorageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final LeagueService leagueService;
    private final StorageService storageService;

    public Team addTeam(TeamDTO request) throws DataIntegrityViolationException, NameNotFoundException{
        Team team = Team.builder()
            .name(request.getName())
            .league(leagueService.getLeague(request.getLeagueName()))
            .build();

        return teamRepository.save(team);
    }

    public Team getTeam(String teamName) throws NameNotFoundException{
        return teamRepository.findByName(teamName).orElseThrow(()-> new NameNotFoundException("Team not found"));
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public Team updateTeam(Integer teamId, TeamDTO request) throws DataIntegrityViolationException, NameNotFoundException, NotFoundException{
        Team dbTeam = teamRepository.findById(teamId).orElseThrow(()-> new NotFoundException());
        dbTeam.setName(request.getName());
        dbTeam.setLeague(leagueService.getLeague(request.getLeagueName()));

        return teamRepository.save(dbTeam);
    }

    public String deleteTeam(Integer teamId){
        teamRepository.deleteById(teamId);
        return "Team deleted";
    }

    //BULK DATA
    public List<Team> addManyTeams(MultipartFile file) throws IOException{
        storageService.saveFile(file);

        Map<String, League> leaguesFromDb = new HashMap<>();
        leagueService.getAllLeagues().forEach(league -> leaguesFromDb.put(league.getName(), league));

        List<Team> teamsOfFile = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        Resource resource = storageService.loadFile(fileName);
        File savedFile = resource.getFile();
        BufferedReader br = new BufferedReader(new FileReader(savedFile, StandardCharsets.UTF_8));
        String line, teamName, leagueName;
        String [] arrayLine;

        while((line = br.readLine()) != null){
            arrayLine = line.split(",");
            teamName = arrayLine[0];
            leagueName = arrayLine[1];

            teamsOfFile.add(Team.builder()
            .name(teamName)
            .league(leaguesFromDb.get(leagueName))
            .build());
        }

        br.close();

        return teamRepository.saveAll(teamsOfFile);
    }
}
