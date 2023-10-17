package com.example.apirestsoccerplayers.controllers.league;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LeagueDTO {
    @NotEmpty
    @Size(max=50)
    @Pattern(regexp = "^[\\p{L}0-9 ]+$")
    private String name;

    @NotEmpty
    @Size(max=50)
    @Pattern(regexp = "[\\p{L}- ]+")
    private String countryName;
}
