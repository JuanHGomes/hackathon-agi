package com.example.demo.mapper;

import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.RegisterResponse;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class RegisterMapper {

    public static RegisterResponse toResponseDTO(User user){
        return new RegisterResponse(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getType()
        );
    }

    public static User toEntity(RegisterRequest registerRequest){
        User user = new User();
        user.setName(registerRequest.name());
        user.setEmail(registerRequest.email());
        user.setType(registerRequest.type());
        user.setPassword(registerRequest.password());
        return user;
    }

    public void updateEntityFromDto(RegisterRequest registerRequest, User user){
        user.setPassword(registerRequest.password());
        user.setType(registerRequest.type());
        user.setName(registerRequest.name());
        user.setEmail(registerRequest.email());
    }
}
