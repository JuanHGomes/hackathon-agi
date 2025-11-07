package com.example.demo.dto.request;

public record TaskRequest(
String title,
String description,
String status,
Long userId
) {
}
