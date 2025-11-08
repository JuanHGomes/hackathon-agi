package com.example.demo.service;

import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.RegisterResponse;
import com.example.demo.entity.User;
import com.example.demo.enums.Type;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.RegisterMapper;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final RegisterMapper registerMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User findUserOrThrow(UUID idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("O usuário de ID: " + idUser + " não foi encontrado!");
                });
    }

    private void checkEmailUnique(String email) {

        boolean emailExists = userRepository.findAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
        if (emailExists) {
            throw new BusinessException("Email " + email + " já está em uso");
        }
    }

    @Transactional
    public RegisterResponse createUser(RegisterRequest registerRequest) {

        try {
            checkEmailUnique(registerRequest.email());

            User newUser = registerMapper.toEntity(registerRequest);

            User userSave = userRepository.save(newUser);

            return registerMapper.toResponseDTO(userSave);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro interno ao criar usuário", e);
        }
    }


    public RegisterResponse findUserById(UUID idUser) {
        try {
            User user = findUserOrThrow(idUser);

            return registerMapper.toResponseDTO(user);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro interno ao buscar o usuário!", e);
        }
    }

    public List<RegisterResponse> findAllUsers(User loggedUser) {

        try {

            if (loggedUser.getType() != Type.ADMIN && loggedUser.getType() != Type.MANAGER) {
                throw new BusinessException("Acesso negado: apenas ADMIN e Gestores podem listar todos os usuários!")
            }

            return userRepository.findAll().stream()
                    .map(registerMapper::toResponseDTO)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Erro interno ao listar usuário!", e);


        }


    }
}
