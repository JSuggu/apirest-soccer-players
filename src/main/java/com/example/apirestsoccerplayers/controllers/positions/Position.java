package com.example.apirestsoccerplayers.controllers.positions;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.example.apirestsoccerplayers.controllers.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="positions")
@Data
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]+")
    @Size(max=10)
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = {
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REMOVE
    }, mappedBy = "positions")
    @JsonIgnore
    private List<Player> players;
}
