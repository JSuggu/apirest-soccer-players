package com.example.apirestsoccerplayers.controllers.player;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.example.apirestsoccerplayers.controllers.country.Country;
import com.example.apirestsoccerplayers.controllers.league.League;
import com.example.apirestsoccerplayers.controllers.positions.Position;
import com.example.apirestsoccerplayers.controllers.team.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="players")
@Data
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @NotEmpty
    @Size(max=100)
    @Pattern(regexp = "[a-zA-Z ]+")
    @Column(nullable = false)
    private String name;

    @NotNull
    @ManyToOne
    private Team team;

    @NotNull
    @ManyToOne
    @JsonIgnore
    private League league;

    @NotNull
    @ManyToOne
    private Country country;

    @ManyToMany(targetEntity = Position.class, cascade = {
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REMOVE
    })
    @JoinTable(
        name = "player_position", 
        joinColumns = @JoinColumn(name = "player_id"), 
        inverseJoinColumns = @JoinColumn(name = "position_id"))
    private List<Position> positions;
}
