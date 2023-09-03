package com.example.apirestsoccerplayers.leagues;

import org.springframework.validation.annotation.Validated;

import com.example.apirestsoccerplayers.countries.Country;

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
@Table(name="leagues")
@Data
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;
    
    @NotEmpty
    @Size(max=50)
    @Pattern(regexp = "[a-zA-Z]+")
    @Column(unique = true)
    private String name;

    @NotNull
    @ManyToOne
    private Country country;
}
