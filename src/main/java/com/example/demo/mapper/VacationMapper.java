package com.example.demo.mapper;

import com.example.demo.dto.request.VacationRequest;
import com.example.demo.dto.response.VacationHistoryResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.VacationHistory;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class VacationMapper {

    private final UserService userService;

    public static VacationHistory map (final VacationRequest request, User originUser){
        return VacationHistory.builder()
                .originUser(originUser)
                .currentUserId(request.currentUserId())
                .initDate(request.initDate())
                .endDate(request.endDate())
                .build();
    }

    public static VacationHistoryResponse toResponse(final VacationHistory vacationHistory){
        return new VacationHistoryResponse(
                vacationHistory.getId(),
                vacationHistory.getCurrentUserId(),
                vacationHistory.getOriginUser().getIdUser(),
                vacationHistory.getInitDate().toString(),
                vacationHistory.getEndDate().toString()
        );
    }
}
