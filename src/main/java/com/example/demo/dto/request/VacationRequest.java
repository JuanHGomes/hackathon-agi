package com.example.demo.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record VacationRequest(
        UUID originUserId,
        UUID currentUserId,
        LocalDateTime initDate,
        LocalDateTime endDate
) {
}
