package com.example.tp2jeepart2;

import com.example.tp2jeepart2.entities.Book;
import com.example.tp2jeepart2.entities.Borrowing;
import com.example.tp2jeepart2.entities.BorrowingStatus;
import com.example.tp2jeepart2.repositories.BookRepository;
import com.example.tp2jeepart2.repositories.BorrowingRepository;
import com.example.tp2jeepart2.services.BorrowingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // PDF Requirement Part IV-A
public class BorrowingServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowingRepository borrowingRepository;

    @InjectMocks
    private BorrowingService borrowingService;

    /**
     * PDF Requirement: Cas 1 - Succès de l'emprunt
     */
    @Test
    void testCheckout_Success() {
        // 1. Arrange (Prepare the data)
        Book book = new Book();
        book.setId(1L);
        book.setStockDisponible(5);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowingRepository.countByUserIdAndStatus(1L, BorrowingStatus.ONGOING)).thenReturn(0L);
        when(borrowingRepository.save(any(Borrowing.class))).thenReturn(new Borrowing());

        // 2. Act (Run the logic)
        Borrowing result = borrowingService.processBorrowing(1L, 1L);

        // 3. Assert (Check the results)
        assertNotNull(result);
        assertEquals(4, book.getStockDisponible()); // Proves Rule: Stock decreases
        verify(bookRepository, times(1)).save(book); // Proves Rule: Save happens
    }

    /**
     * PDF Requirement: Cas 2 - Échec si le stock est épuisé
     */
    @Test
    void testCheckout_Fail_NoStock() {
        // 1. Arrange
        Book emptyBook = new Book();
        emptyBook.setId(2L);
        emptyBook.setStockDisponible(0); // ZERO STOCK

        when(bookRepository.findById(2L)).thenReturn(Optional.of(emptyBook));

        // 2. Act & Assert
        // This checks if your code correctly "Throws" the exception you saw in Postman
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            borrowingService.processBorrowing(2L, 1L);
        });

        assertTrue(exception.getMessage().contains("out of stock"));
        verify(borrowingRepository, never()).save(any()); // Proves: No borrowing is saved if stock is 0
    }
}