package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User findUserOrThrow(UUID idUser){
        return userRepository.findById(idUser)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("O usuário de ID: " + idUser + " não foi encontrado!");
                });
    }

    private void CheckEmailUnique(String email){

        boolean emailExists = userRepository.findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
        if (emailExists){
            throw new BusinessException("Email " + email + " já está em uso");
        }
    }

    public User findUserById(UUID id){

        return userRepository.findById(id).orElseThrow();

    }






}
