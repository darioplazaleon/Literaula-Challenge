package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InfoAuthor(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Integer birthDate,
        @JsonAlias("death_year") Integer deathDate
) {
}
