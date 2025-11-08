package com.example.demo.dto.response;

import com.example.demo.enums.Role;

import java.util.UUID;

public record RegisterResponse(

        UUID idUser,
        String name,
        String email,
        Role role
) {}
