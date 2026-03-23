package com.example.tp2jeepart2.services;

import com.example.tp2jeepart2.entities.Book;
import com.example.tp2jeepart2.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // 1. Récupérer tous les livres
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 2. Chercher par ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // 3. Chercher par ISBN (Demande du prof)
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    // 4. Sauvegarder un livre (avec vérification d'ISBN unique)
    public Book saveBook(Book book) {
        // On vérifie si l'ISBN existe déjà en base
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new RuntimeException("Erreur : Cet ISBN existe déjà dans la bibliothèque !");
        }
        return bookRepository.save(book);
    }

    // 5. Mettre à jour un livre
    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));

        book.setTitle(bookDetails.getTitle());
        book.setIsbn(bookDetails.getIsbn());
        book.setStockDisponible(bookDetails.getStockDisponible());

        return bookRepository.save(book);
    }

    // 6. Supprimer un livre
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}