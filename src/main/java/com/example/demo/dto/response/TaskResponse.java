package com.example.demo.dto.response;

import com.example.demo.enums.Status;

import java.util.UUID;

public record TaskResponse (Long id,
                            String title,
                            String description,
                            Status status,
                            UUID userId){
}
