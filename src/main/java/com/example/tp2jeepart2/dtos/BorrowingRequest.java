package com.example.tp2jeepart2.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowingRequest {
    @NotNull(message = "Book ID is required")
    private Long bookId; // Changed from String to Long

    @NotNull(message = "User ID is required")
    private Long userId; // Changed from String to Long
}