package com.example.tp2jeepart2.services;

import com.example.tp2jeepart2.entities.Author;
import com.example.tp2jeepart2.entities.Book;
import com.example.tp2jeepart2.entities.Category;
import com.example.tp2jeepart2.repositories.AuthorRepository;
import com.example.tp2jeepart2.repositories.BookRepository;
import com.example.tp2jeepart2.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // --- AJOUT : Nécessaire pour récupérer les noms pour le DTO ---
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    // -------------------------------------------------------------

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    // 4. Sauvegarder un livre (Logic fix : On va chercher les objets réels en DB)
    public Book saveBook(Book book) {
        // A. Vérification ISBN
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new RuntimeException("Erreur : Cet ISBN existe déjà dans la bibliothèque !");
        }

        // B. LIEN AUTEUR : Si Postman envoie {"author": {"id": 1}}
        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
            Author realAuthor = authorRepository.findById(book.getAuthor().getId())
                    .orElseThrow(() -> new RuntimeException("Auteur introuvable en base de données"));
            book.setAuthor(realAuthor); // L'objet book contient maintenant le NOM de l'auteur
        }

        // C. LIEN CATÉGORIES : Si Postman envoie {"categories": [{"id": 1}]}
        if (book.getCategories() != null && !book.getCategories().isEmpty()) {
            Set<Long> ids = book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());

            List<Category> realCategories = categoryRepository.findAllById(ids);
            book.setCategories(new HashSet<>(realCategories)); // L'objet book contient maintenant les LIBELLÉS
        }

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));

        book.setTitle(bookDetails.getTitle());
        book.setIsbn(bookDetails.getIsbn());
        book.setStockDisponible(bookDetails.getStockDisponible());

        // Optionnel : Mise à jour de l'auteur lors d'un PUT
        if (bookDetails.getAuthor() != null && bookDetails.getAuthor().getId() != null) {
            Author author = authorRepository.findById(bookDetails.getAuthor().getId()).orElse(null);
            book.setAuthor(author);
        }

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
