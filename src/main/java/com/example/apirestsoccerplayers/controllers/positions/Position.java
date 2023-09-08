package com.example.apirestsoccerplayers.controllers.positions;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.example.apirestsoccerplayers.controllers.player.Player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name="positions")
@Data
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]+")
    @Size(max=10)
    @Column(nullable = false)
    private String name;

    @ManyToMany(targetEntity = Player.class)
    private List player;
}
