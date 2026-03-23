package com.example.tp2jeepart2.controllers;

import com.example.tp2jeepart2.entities.Book;
import com.example.tp2jeepart2.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // GET /api/v1/books : Liste tous les livres
    @GetMapping
    public List<Book> getAll() {
        return bookService.getAllBooks();
    }

    // GET /api/v1/books/{id} : Un seul livre par son ID
    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
    }

    // GET /api/v1/books/isbn/{isbn} : Recherche par ISBN
    @GetMapping("/isbn/{isbn}")
    public Book getByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Aucun livre avec cet ISBN"));
    }

    // POST /api/v1/books : Ajouter un livre (ADMIN)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    // PUT /api/v1/books/{id} : Modifier un livre
    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    // DELETE /api/v1/books/{id} : Supprimer un livre
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "Livre supprimé avec succès !";
    }
}