package com.example.demo.dto.response;

import com.example.demo.enums.Role;

import java.util.UUID;

public record LoginResponse(

        UUID idName,
        String name,
        Role role
) {}
