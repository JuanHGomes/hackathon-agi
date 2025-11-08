package com.example.demo.dto.response;

import java.util.UUID;

public record VacationHistoryResponse (Long id, UUID originUserId, UUID currentUserId, String initDate, String endDate) {
}
