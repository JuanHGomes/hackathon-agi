package com.example.demo.dto.response;

import com.example.demo.entity.User;
import com.example.demo.enums.Type;

import java.util.UUID;

public record RegisterResponse(

        UUID idUser,
        String name,
        String email,
        Type type
) extends User {}
