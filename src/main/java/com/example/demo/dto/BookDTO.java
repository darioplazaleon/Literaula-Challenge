package com.example.demo.dto;

public record BookDTO (
        Long id,
        String title,
        Integer totalDownloads,
        String author,
        String languages
){}
