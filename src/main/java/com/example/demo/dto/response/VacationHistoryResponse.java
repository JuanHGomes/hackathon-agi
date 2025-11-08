package com.example.demo.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record VacationHistoryResponse (Long id, UUID originUserId, UUID currentUserId, Long taskId, LocalDate initDate, LocalDate endDate) {
}
