package com.example.demo.controller;

import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.RegisterResponse;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.mapper.RegisterMapper;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegisterResponse> createUser(@RequestBody @Valid RegisterRequest request){
        RegisterResponse createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegisterResponse> findUserById(@PathVariable UUID idUser){
        RegisterResponse response = RegisterMapper.toResponseDTO(userService.findUserById(idUser));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RegisterResponse>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable UUID id,
            @RequestParam UUID requesterId,
            @RequestBody User updatedData) {

        User requester = userService.findUserById(requesterId); // 👈 Corrigido: era id, agora requesterId
        User target = userService.findUserById(id);

        if (requester.getRole() == Role.ADMIN) {
            User updated = userService.update(id, updatedData);
            return ResponseEntity.ok(updated);
        }

        if (requester.getRole() == Role.MANAGER) {
            if (target.getRole() == Role.EMPLOYEE) {
                User updated = userService.update(id, updatedData);
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.status(403)
                        .body("Gestor só pode atualizar funcionários (EMPLOYEE).");
            }
        }

        if (requester.getRole() == Role.EMPLOYEE) {
            if (!requester.getIdUser().equals(id)) {
                return ResponseEntity.status(403)
                        .body("Você só pode atualizar o seu próprio perfil!");
            }
            User updated = userService.update(id, updatedData);
            return ResponseEntity.ok(updated);
        }

        return ResponseEntity.status(403).body("Acesso negado!");
    }

    // 👇 Novo endpoint para inativar usuário (somente ADMIN pode)
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUser(
            @PathVariable UUID id,
            @RequestParam UUID requesterId) {

        User requester = userService.findUserById(requesterId);

        if (requester.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).body("Apenas ADMIN pode inativar usuários.");
        }

        userService.deactivateUser(id);
        return ResponseEntity.ok("Usuário inativado com sucesso.");
    }

    //  Novo endpoint para ativar usuário (somente ADMIN pode)
    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activateUser(
            @PathVariable UUID id,
            @RequestParam UUID requesterId) {

        User requester = userService.findUserById(requesterId);

        if (requester.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).body("Apenas ADMIN pode ativar usuários.");
        }

        userService.activateUser(id);
        return ResponseEntity.ok("Usuário ativado com sucesso.");
    }
}