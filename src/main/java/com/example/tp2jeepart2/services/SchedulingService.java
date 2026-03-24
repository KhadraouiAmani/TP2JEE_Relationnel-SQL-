package com.example.tp2jeepart2.services;

import com.example.tp2jeepart2.entities.Borrowing;
import com.example.tp2jeepart2.entities.BorrowingStatus;
import com.example.tp2jeepart2.repositories.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@EnableScheduling
public class SchedulingService {

    @Autowired
    private BorrowingRepository borrowingRepository;

    /**
     * Tâche planifiée (Partie V-3 du sujet)
     * S'exécute chaque nuit à minuit (cron = "0 0 0 * * *")
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void checkOverdueBorrowings() {
        System.out.println("DEBUG: Début de la vérification des retards...");

        // 1. Récupérer tous les emprunts en cours (ONGOING)
        // Note: Amani doit ajouter cette méthode dans BorrowingRepository
        List<Borrowing> activeBorrowings = borrowingRepository.findByStatus(BorrowingStatus.ONGOING);

        for (Borrowing b : activeBorrowings) {
            // 2. Si la date de retour est passée
            if (b.getReturnDate().isBefore(LocalDate.now())) {
                // 3. Changer le statut en OVERDUE
                b.setStatus(BorrowingStatus.OVERDUE);

                // 4. Sauvegarder la modification dans H2
                borrowingRepository.save(b);

                System.out.println("DEBUG: L'emprunt ID " + b.getId() + " est maintenant en RETARD.");
            }
        }

        System.out.println("DEBUG: Fin de la vérification.");
    }
}