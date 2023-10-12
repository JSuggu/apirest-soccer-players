package com.example.apirestsoccerplayers.controllers.player;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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
public class PlayerDTO {

    @Size(max=100)
    @Pattern(regexp = "[a-zA-Z ]+")
    private String name;

    @Size(max = 100)
    @Pattern(regexp = "[a-zA-ZñÑ ]+")
    private String countryName;

    @Size(min = 10, max=10)
    @Pattern(regexp = "[0-9-]+")
    private String birthday;

    @Pattern(regexp = "[a-zA-ZñÑ ]+")
    @Size(max=100)
    private String teamName;

    @Size(max=50)
    @Pattern(regexp = "[a-zA-ZñÑ ]+")
    private String leagueName;

    @Size(max=100)
    @Pattern(regexp = "[a-zA-Z,]+")
    private String positionsName;
}
