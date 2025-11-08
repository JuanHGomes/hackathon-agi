package com.example.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record TaskRequest(
@NotEmpty String title,
String description,
Long userId
) {
}
