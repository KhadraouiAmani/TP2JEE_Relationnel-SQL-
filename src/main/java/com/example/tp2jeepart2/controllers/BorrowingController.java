package com.example.tp2jeepart2.controllers;

import com.example.tp2jeepart2.entities.Borrowing;
import com.example.tp2jeepart2.services.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/borrowings")
public class BorrowingController {

    @Autowired private BorrowingService borrowingService;

    @PostMapping("/checkout")
    public Borrowing checkout(@RequestParam Long bookId, @RequestParam Long userId) {
        return borrowingService.processBorrowing(bookId, userId);
    }

    @GetMapping("/user/{userId}")
    public List<Borrowing> getUserBorrowings(@PathVariable Long userId) {
        return borrowingService.getMemberBorrowings(userId);
    }
}