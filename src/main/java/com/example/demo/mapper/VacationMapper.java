package com.example.demo.mapper;

import com.example.demo.dto.request.VacationRequest;
import com.example.demo.dto.response.VacationHistoryResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.VacationHistory;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class VacationMapper {

    private final UserService userService;

    public static VacationHistory map (final VacationRequest request, User originUser, User currentUser){
        return VacationHistory.builder()
                .originUser(originUser)
                .currentUser(currentUser)
                .initDate(request.initDate())
                .endDate(request.endDate())
                .build();
    }

    public static VacationHistoryResponse toResponse(final VacationHistory vacationHistory){
        return new VacationHistoryResponse(
                vacationHistory.getId(),
                vacationHistory.getCurrentUser().getIdUser(),
                vacationHistory.getOriginUser().getIdUser(),
                vacationHistory.getInitDate().toString(),
                vacationHistory.getEndDate().toString()
        );
    }
}
