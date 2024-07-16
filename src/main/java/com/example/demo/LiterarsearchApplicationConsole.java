package com.example.demo;

import com.example.demo.client.GutendexApiClient;
import com.example.demo.principal.Principal;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterarsearchApplicationConsole implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiterarsearchApplicationConsole.class);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(bookRepository, authorRepository);
        principal.showMenu();
    }
}
