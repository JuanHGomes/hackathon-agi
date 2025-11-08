package com.example.demo.controller;

import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.RegisterResponse;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.mapper.RegisterMapper;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários e controle de acesso.")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Cria um novo usuário",
            description = "Cria um usuário e atribui sua role inicial.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário criado com sucesso.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RegisterResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados inválidos fornecidos no corpo da requisição.")
            }
    )
    @PostMapping
    public ResponseEntity<RegisterResponse> createUser(@RequestBody @Valid RegisterRequest request){

        RegisterResponse createdUser = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @Operation(
            summary = "Localiza um usuário pelo Id",
            description = "Retorna os detalhes de um usuário específico.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário encontrado e retornado.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RegisterResponse.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RegisterResponse> findUserById(@PathVariable UUID idUser){

        RegisterResponse response = RegisterMapper.toResponseDTO(userService.findUserById(idUser));

        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Lista todos os usuários",
            description = "Retorna uma lista de todos os usuários registrados no sistema.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de usuários retornada com sucesso.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RegisterResponse.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<RegisterResponse>> findAllUsers(){

        return ResponseEntity.ok(userService.findAllUsers());
    }

    @Operation(
            summary = "Atualiza as informações de um usuário",
            description = "Permite a atualização de dados do usuário com base nas regras de permissão (ADMIN pode tudo; MANAGER pode atualizar apenas EMPLOYEE; EMPLOYEE pode atualizar apenas a si mesmo).",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário atualizado com sucesso.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acesso negado devido a regras de permissão (ex: Gestor tentando alterar Admin).",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário alvo ou requisitante não encontrado.")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable UUID id,
            @RequestParam UUID requesterId,
            @RequestBody User updatedData) {



        User requester = userService.findUserById(requesterId);
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
}