package com.example.tp2jeepart2.repositories;

import com.example.tp2jeepart2.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Cette interface te donne déjà accès à : save(), findAll(), deleteById(), etc.
}
