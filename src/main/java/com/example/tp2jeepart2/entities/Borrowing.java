package com.example.tp2jeepart2.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "borrowings")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // L'ID de l'utilisateur qui emprunte

    private LocalDate borrowDate;
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING) // Pour enregistrer le texte (ONGOING) au lieu d'un chiffre
    private BorrowingStatus status;

    @ManyToOne // Plusieurs emprunts peuvent concerner le même livre
    @JoinColumn(name = "book_id")
    private Book book;
}
