package com.example.demo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer birthDate;
    private Integer deathDate;

    @OneToMany(mappedBy = "authors", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    @Override
    public String toString() {
        return """
                Autor; %s
                Fecha de nacimiento: %d
                Fecha de fallecimiento: %d
                Libros: %s
                """.formatted(this.name, this.birthDate, this.deathDate,
                this.books.stream().map(Book::getTitle).toList().toString()
        );
    }

    public Author(){
    }

    public Author(InfoAuthor infoAuthor, Book book) {
        this.name = infoAuthor.name();
        this.birthDate = infoAuthor.birthDate();
        this.deathDate = infoAuthor.deathDate();
        this.books.add(book);
    }

    public Author(InfoAuthor infoAuthor) {
        this.name = infoAuthor.name();
        this.birthDate = infoAuthor.birthDate();
        this.deathDate = infoAuthor.deathDate();
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this);
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Integer birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Integer deathDate) {
        this.deathDate = deathDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
