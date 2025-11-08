package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);

    //  Busca usuário ativo por e-mail (útil para autenticação)
    Optional<User> findUserByEmailAndActiveTrue(String email);

    //  Lista todos os usuários ativos
    List<User> findAllByActiveTrue();

    //  Lista todos os usuários inativos (opcional, para painel de administração)
    List<User> findAllByActiveFalse();
}