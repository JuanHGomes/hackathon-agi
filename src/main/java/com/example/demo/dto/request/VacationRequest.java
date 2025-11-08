package com.example.demo.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record VacationRequest(
        String originUserId,
        String currentUserId,
        LocalDateTime initDate,
        LocalDateTime endDate
) {
}
