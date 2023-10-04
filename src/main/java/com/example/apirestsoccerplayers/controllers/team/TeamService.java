package com.example.apirestsoccerplayers.controllers.team;

import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.apirestsoccerplayers.controllers.league.LeagueService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final LeagueService leagueService;

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
}
