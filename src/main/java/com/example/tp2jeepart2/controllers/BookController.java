package com.example.tp2jeepart2.controllers;

import com.example.tp2jeepart2.dtos.BookDTO;
import com.example.tp2jeepart2.mappers.BookMapper;
import com.example.tp2jeepart2.entities.Book;
import com.example.tp2jeepart2.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // --- AJOUT : Injection de ton Mapper ---
    @Autowired
    private BookMapper bookMapper;

    // GET /api/v1/books : Retourne une liste de DTOs
    @GetMapping
    public List<BookDTO> getAll() {
        return bookService.getAllBooks().stream()
                .map(bookMapper::toDto) // Conversion Entité -> DTO
                .collect(Collectors.toList());
    }

    // GET /api/v1/books/{id} : Retourne un DTO
    @GetMapping("/{id}")
    public BookDTO getById(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        return bookMapper.toDto(book);
    }

    // GET /api/v1/books/isbn/{isbn}
    @GetMapping("/isbn/{isbn}")
    public BookDTO getByIsbn(@PathVariable String isbn) {
        Book book = bookService.getBookByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Aucun livre avec cet ISBN"));
        return bookMapper.toDto(book);
    }

    // POST /api/v1/books : (ADMIN uniquement via SecurityConfig)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retourne 201 Created (Exigence IV-B)
    public BookDTO create(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return bookMapper.toDto(savedBook);
    }

    // PUT /api/v1/books/{id} : (ADMIN uniquement)
    @PutMapping("/{id}")
    public BookDTO update(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return bookMapper.toDto(updatedBook);
    }

    // DELETE /api/v1/books/{id} : (ADMIN uniquement)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "Livre supprimé avec succès !";
    }
}