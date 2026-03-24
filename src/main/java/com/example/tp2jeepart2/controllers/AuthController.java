package com.example.tp2jeepart2.controllers;

import com.example.tp2jeepart2.dtos.AuthResponse;
import com.example.tp2jeepart2.dtos.LoginRequest;
import com.example.tp2jeepart2.repositories.UserRepository;
import com.example.tp2jeepart2.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> new AuthResponse(jwtUtil.generateToken(user.getUsername())))
                .orElseThrow(() -> new RuntimeException("Invalid username or password!"));
    }
}