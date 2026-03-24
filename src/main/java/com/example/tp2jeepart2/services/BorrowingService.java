package com.example.tp2jeepart2.services;

import com.example.tp2jeepart2.entities.Book;
import com.example.tp2jeepart2.entities.Borrowing;
import com.example.tp2jeepart2.entities.BorrowingStatus;
import com.example.tp2jeepart2.repositories.BookRepository;
import com.example.tp2jeepart2.repositories.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingService {

    @Autowired private BorrowingRepository borrowingRepository;
    @Autowired private BookRepository bookRepository;

    @Transactional // Requirement: Ensures Atomicity (Part III of PDF)
    public Borrowing processBorrowing(Long bookId, Long userId) {

        // 1. Find the book (using JPA's Optional)
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Error: Book not found"));

        // 2. Rule: Check stock (Requirement Part III)
        if (book.getStockDisponible() <= 0) {
            throw new RuntimeException("Error: Book is out of stock!");
        }

        // 3. Rule: Check user limit (Requirement Part III)
        long activeCount = borrowingRepository.countByUserIdAndStatus(userId, BorrowingStatus.ONGOING);
        if (activeCount >= 3) {
            throw new RuntimeException("Error: User already has 3 active borrowings");
        }

        // 4. Action: Decrease stock
        book.setStockDisponible(book.getStockDisponible() - 1);
        bookRepository.save(book);

        // 5. Action: Create the borrowing record
        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setUserId(userId);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setReturnDate(LocalDate.now().plusDays(14));
        borrowing.setStatus(BorrowingStatus.ONGOING);

        return borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getMemberBorrowings(Long userId) {
        return borrowingRepository.findByUserId(userId);
    }
}