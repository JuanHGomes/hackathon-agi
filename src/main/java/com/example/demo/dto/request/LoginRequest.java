package com.example.demo.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "O e-mail do usuário é obrigatório!")
        @Email(message = "O e-mail precisa deve ter formato válido")
        String email,

        @NotBlank(message = "A senha é obrigatória!")
        String senha

) { }
