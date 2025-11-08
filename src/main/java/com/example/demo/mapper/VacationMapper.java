package com.example.demo.mapper;

import com.example.demo.dto.request.VacationRequest;
import com.example.demo.dto.response.VacationHistoryResponse;
import com.example.demo.entity.VacationHistory;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class VacationMapper {

    private final UserService userService;

    public static VacationHistory map (final VacationRequest request){
        return VacationHistory.builder()
                .originUser(request.originUserId())
                .currentUserId(request.currentUserId())
                .initDate(request.initDate())
                .endDate(request.endDate())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static VacationHistoryResponse toRequest(final VacationHistory vacationHistory){
        return new VacationHistoryResponse(
                vacationHistory.getId(),
                vacationHistory.getCurrentUser().getIdUser(),
                vacationHistory.getOriginUser().getIdUser(),
                vacationHistory.getInitDate().toString(),
                vacationHistory.getEndDate().toString()
        );
    }
}
