package com.example.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String languages;
    private Integer totalDownloads;

    @ManyToOne
    private Author authors;



    @Override
    public String toString() {
        return """
                -----------------LIBRO---------------
                Titulo: %s
                Autor: %s
                Idioma: %s
                Descargas: %d
                -------------------------------------
                """.formatted(this.title, this.authors.getName(), this.languages, this.totalDownloads);
    }

     public Book() {
     }

     public Book(String title, List<String> language, Integer totalDownloads) {
        this.title = title;
        this.totalDownloads = totalDownloads;
        this.languages = language.get(0);
     }

     public void setAuthor(Author author) {
        this.authors = author;
        if (!author.getBooks().contains(this)) {
            author.getBooks().add(this);
        }
     }

    public Integer getTotalDownloads() {
        return totalDownloads;
    }

    public void setTotalDownloads(Integer totalDownloads) {
        this.totalDownloads = totalDownloads;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Author getAuthors() {
        return authors;
    }

    public void setAuthors(Author authors) {
        this.authors = authors;
    }
}
