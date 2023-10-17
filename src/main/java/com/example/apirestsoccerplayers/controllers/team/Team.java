package com.example.apirestsoccerplayers.controllers.team;

import org.springframework.validation.annotation.Validated;

import com.example.apirestsoccerplayers.controllers.league.League;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="teams")
@Data
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @NotEmpty
    @Pattern(regexp = "^[\\p{L}\\p{M}0-9-.'() ]+$")
    @Size(max=100)
    @Column(nullable = false)
    private String name;

    @NotNull
    @ManyToOne
    League league;
}
