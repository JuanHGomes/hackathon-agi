package com.example.demo.service;

import com.example.demo.dto.request.VacationRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.entity.VacationHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationHistoryService {

    private final TaskService taskService;
    private final UserService userService;

    public VacationHistory newVacationHistory(VacationRequest request){

    User newCurrentUser = userService.findUserById(request.currentUserId());

    List<Task> pendingTasks = taskService.listByUserAndStatusPending(request.currentUserId());

    pendingTasks.forEach(task -> task.setCurrentUser(newCurrentUser));

    return VacationHistory.builder()
            .originUser(userService.findUserById(request.currentUserId()))
            .currentUser(newCurrentUser)
            .initDate(request.initDate())
            .endDate(request.endDate())
            .build();

    }



}
