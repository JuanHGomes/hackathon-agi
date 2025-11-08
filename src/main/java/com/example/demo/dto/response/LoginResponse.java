package com.example.demo.dto.response;

import com.example.demo.enums.Type;

import java.util.UUID;

public record LoginResponse(

        UUID idName,
        String name,
        Type type
) {}
