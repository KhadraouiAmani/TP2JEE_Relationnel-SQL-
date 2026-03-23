package com.example.tp2jeepart2.repositories;

import com.example.tp2jeepart2.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Permet de gérer les catégories (Roman, Science, etc.) en base SQL
}