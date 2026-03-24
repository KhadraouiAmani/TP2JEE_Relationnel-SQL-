package com.example.tp2jeepart2.repositories;

import com.example.tp2jeepart2.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);
}

