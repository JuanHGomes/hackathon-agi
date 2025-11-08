package com.example.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record TaskRequest(
@NotEmpty String title,
String description,
UUID userId
) {
}
