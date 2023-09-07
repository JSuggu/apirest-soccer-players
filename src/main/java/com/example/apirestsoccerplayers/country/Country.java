package com.example.apirestsoccerplayers.country;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="countries")
@Data
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;

    @NotEmpty
    @Size(max = 100)
    @Pattern(regexp = "[a-zA-ZñÑ]+")
    @Column(unique = true)
    private String name;
}
