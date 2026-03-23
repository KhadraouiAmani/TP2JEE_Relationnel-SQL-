package com.example.tp2jeepart2.services;

import com.example.tp2jeepart2.entities.Book;
import com.example.tp2jeepart2.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    @Autowired private BookRepository bookRepository;

    public List<Book> getAllBooks() { return bookRepository.findAll(); }

    public Book saveBook(Book book) {
        // Vérification de l'ISBN unique
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new RuntimeException("Duplicate ISBN Error!");
        }
        return bookRepository.save(book);
    }
}