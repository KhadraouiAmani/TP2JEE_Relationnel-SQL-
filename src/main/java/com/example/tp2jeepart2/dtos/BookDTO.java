package com.example.tp2jeepart2.dtos;

import lombok.Data;
import java.util.List;

@Data
public class BookDTO {
    private Long id; // Changed from String to Long
    private String isbn;
    private String title;
    private int stockDisponible;
    private String authorName;
    private List<String> categoryNames;
}