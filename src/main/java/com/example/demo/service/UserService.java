package com.example.demo.service;

import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.RegisterResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.RegisterMapper;
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
    private final RegisterMapper registerMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User findUserOrThrow(UUID idUser){
        return userRepository.findById(idUser)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("O usuário de ID: " + idUser + " não foi encontrado!");
                });
    }

    private void checkEmailUnique(String email){

        boolean emailExists = userRepository.findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
        if (emailExists){
            throw new BusinessException("Email " + email + " já está em uso");
        }
    }

    public User findUserById(UUID id){

        return userRepository.findById(id).orElseThrow();

    }

    @Transactional
    public RegisterResponse createUser(RegisterRequest registerRequest) {

        try{
             checkEmailUnique(registerRequest.email());

             User newUser = registerMapper.toEntity(registerRequest);

             User userSave = userRepository.save(newUser);

            RegisterResponse response = registerMapper.toResponseDTO(userSave);
            return response;

        } catch (BusinessException e) {
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Erro interno ao criar usuário", e);
        }
    }






}
