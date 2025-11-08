package com.example.demo.controller;

import com.example.demo.dto.request.VacationRequest;
import com.example.demo.dto.response.VacationHistoryResponse;
import com.example.demo.mapper.VacationMapper;
import com.example.demo.service.VacationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vacation")
@RequiredArgsConstructor
public class VacationController {
    private final VacationHistoryService vacationService;
    @PostMapping
    public VacationHistoryResponse newVacatio(@RequestBody VacationRequest request){
        return VacationMapper.toResponse(vacationService.newVacationHistory(request));
    }
}
