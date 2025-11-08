package com.example.demo.dto.response;

import com.example.demo.enums.Role;

import java.util.UUID;

public record LoginResponse(
        String token
) {}
