package com.example.demo.dto.response;

public record TaskResponse (Long id,
                            String title,
                            String description,
                            String status,
                            Long userId){
}
