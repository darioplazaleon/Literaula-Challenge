package com.example.demo.repository;

import com.example.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository  extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

    @Query("SELECT a FROM Author a WHERE a.birthDate <= :year AND (a.deathDate IS NULL OR a.deathDate >= :year)")
    List<Author> findAuthorsAliveInYear(@Param("year") Integer year);
}
