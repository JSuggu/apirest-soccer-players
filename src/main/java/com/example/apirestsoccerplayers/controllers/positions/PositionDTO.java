package com.example.apirestsoccerplayers.controllers.positions;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Component
public class PositionDTO {
    @NotEmpty
    @Pattern(regexp = "^[\\p{L} ]+$")
    @Size(max=50)
    private String name;
}
