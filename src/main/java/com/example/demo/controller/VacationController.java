package com.example.demo.controller;

import com.example.demo.dto.request.VacationRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.dto.response.VacationHistoryResponse;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.mapper.VacationMapper;
import com.example.demo.service.VacationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vacation")
@RequiredArgsConstructor
public class VacationController {
    private final VacationHistoryService vacationService;
    @PostMapping
    public List<TaskResponse> newVacatio(@RequestBody VacationRequest request){
        return vacationService.newVacationHistory(request).stream()
                .map(TaskMapper::toResponse).toList();
    }
}
