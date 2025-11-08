package com.example.demo.controller;

import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.RegisterResponse;
import com.example.demo.entity.User;
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
}
