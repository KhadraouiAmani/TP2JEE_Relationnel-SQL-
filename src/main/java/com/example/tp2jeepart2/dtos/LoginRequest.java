package com.example.tp2jeepart2.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

