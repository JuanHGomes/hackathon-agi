package com.example.demo.service;

import com.example.demo.dto.request.VacationRequest;
import com.example.demo.dto.response.VacationHistoryResponse;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.mapper.VacationMapper;
import com.example.demo.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationHistoryService {

    private final TaskService taskService;
    private final UserService userService;
    private final VacationRepository vacationRepository;

    public VacationHistoryResponse newVacationHistory(VacationRequest request){

        User originUser = userService.findUserById(request.originUserId());
        User currentUser = userService.findUserById(request.currentUserId());

        List<Task> pendingTasks = taskService.listByUserAndStatusPending(request.currentUserId());

        pendingTasks.forEach(task -> task.setCurrentUser(currentUser));

        return VacationMapper.toResponse(
                vacationRepository.save(
                        VacationMapper.map(request, originUser, currentUser)));

    }
}
