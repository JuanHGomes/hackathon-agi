package com.example.demo.dto.request;

import com.example.demo.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(

//        @NotBlank(message = "O nome do usuário é obrigatório!")
        String name,

//        @NotBlank(message = "O e-mail do usuário é obrigatório!")
//        @Email(message = "O e-mail precisa deve ter formato válido")
        String email,

//        @NotBlank(message = "A senha é obrigatória!")
        String password,

//        @NotNull(message = "O perfil do usuário é obrigatório!")
        Role role

) {
}
