package com.example.demo.dto.response;

import com.example.demo.enums.Status;
import lombok.Builder;

import java.util.Optional;
import java.util.UUID;

@Builder
public record TaskResponse (Long id,
                            String title,
                            String description,
                            Status status,
                            UUID userId){
}
