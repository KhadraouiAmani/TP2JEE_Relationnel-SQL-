package com.example.tp2jeepart2.repositories;

import com.example.tp2jeepart2.entities.Borrowing;
import com.example.tp2jeepart2.entities.BorrowingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    // --- LIGNE À AJOUTER POUR LE SCHEDULING ---
    List<Borrowing> findByStatus(BorrowingStatus status);
    // ------------------------------------------

    // Compte combien d'emprunts en cours a un utilisateur (Règle des 3 livres max)
    long countByUserIdAndStatus(Long userId, BorrowingStatus status);

    // Liste des emprunts d'un membre
    List<Borrowing> findByUserId(Long userId);
}