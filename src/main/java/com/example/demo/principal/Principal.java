package com.example.demo.principal;

import com.example.demo.client.GutendexApiClient;
import com.example.demo.model.*;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.DataChange;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final Scanner keyboard = new Scanner(System.in);
    private final GutendexApiClient gutendexApiClient = new GutendexApiClient();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final DataChange convert = new DataChange();
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Principal(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu() {
        var option = -1;
        while (true) {
            var menu = """
                    1- buscar libro por titulo
                    2- listar libros registrados
                    3- listar autores registrados
                    4- listar autores vivos en un determinado anio
                    5- listar libros por idioma
                    0- salir
                    """;
            System.out.println(menu);
            option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    listAllBooks();
                    break;
                case 3:
                    listAllAuthors();
                    break;
                case 4:
                    listAuthorsAliveInYear();
                    break;
                case 5:
                    listBooksPerLanguage();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private void searchBookByTitle() {
        System.out.println("Ingrese el titulo del libro que busca: ");
        var bookName = keyboard.nextLine();
        DataApi dataApi = searchBook(bookName);

        Optional<Book> bookOptional = dataApi.results().stream()
                        .map(r ->{
                            InfoAuthor infoAuthor = r.authors().get(0);
                            Author author = authorRepository.findByName(infoAuthor.name())
                                    .orElseGet(() -> {
                                        Author newAuthor = new Author(infoAuthor);
                                        authorRepository.save(newAuthor);
                                        return newAuthor;
                                    });

                            Book book = new Book(r.title(), r.languages(), r.totalDownloads());
                            book.setAuthor(author);
                            author.addBook(book);
                            return book;
                        })
                                .findFirst();

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            bookRepository.save(book);

            System.out.println(book);
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listAllBooks() {
        bookRepository.findAll().forEach(System.out::println);
    }

    private void listAllAuthors(){
        authorRepository.findAll().forEach(System.out::println);
    }

    private void listAuthorsAliveInYear(){
        System.out.println("Ingrese el anio: ");
        Integer year = keyboard.nextInt();
        keyboard.nextLine();

        List<Author> authorList = authorRepository.findAuthorsAliveInYear(year);
        if(!authorList.isEmpty()){
            authorList.forEach(System.out::println);
        } else {
            System.out.println("No se encontro un autor vivo en ese anio");
        }

    }

    private void listBooksPerLanguage(){
        System.out.println("Ingrese el idioma de los libros que desea: ");
        System.out.println("""
                es: Español
                pt: Portugués
                fr: Francés
                en: Inglés
                """);

        String language = keyboard.nextLine();

        List<Book> books = bookRepository.findBookByLanguage(language);

        if(!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron libros con el lenguaje seleccionado");
        }

    }

    private DataApi searchBook(String bookName) {
        var json = gutendexApiClient.getDataBooks(URL_BASE + "?search=" + bookName);
        return convert.obtainData(json, DataApi.class);
    }

}
