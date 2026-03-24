package com.example.tp2jeepart2.config;

import com.example.tp2jeepart2.entities.User;
import com.example.tp2jeepart2.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            userRepository.deleteAll(); // Clears H2 on every restart
            userRepository.save(new User(null, "admin", encoder.encode("admin123"), Set.of("ROLE_ADMIN")));
            userRepository.save(new User(null, "member1", encoder.encode("pass123"), Set.of("ROLE_USER")));
            System.out.println("DEBUG: SQL/H2 Users initialized.");
        };
    }
}