package com.example.apirestsoccerplayers.controllers.team;

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
public class TeamDTO {
    @NotEmpty
    @Pattern(regexp = "[a-zA-ZñÑ ]+")
    @Size(max=100)
    private String name;

    @NotEmpty
    @Size(max=50)
    @Pattern(regexp = "[a-zA-ZñÑ ]+")
    private String leagueName;
}
