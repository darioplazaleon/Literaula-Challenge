package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InfoBook (
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<InfoAuthor> authors,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") Integer totalDownloads
) {}
