package com.example.demo.service;

import com.example.demo.dto.request.VacationRequest;
import com.example.demo.dto.response.VacationHistoryResponse;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.entity.VacationHistory;
import com.example.demo.mapper.VacationMapper;
import com.example.demo.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationHistoryService {

    private final String SCHEDULED_CHANGE_USER_OF_ENDED_VACATION = "0 0 0 * * ?";

    private final TaskService taskService;
    private final UserService userService;
    private final VacationRepository vacationRepository;

    public VacationHistory newVacationHistory(VacationRequest request){

        User originUser = userService.findUserById(request.originUserId());
        User currentUser = userService.findUserById(request.currentUserId());

        List<Task> emAndamentoTasks = taskService.listEmAndamentoTasks(request.currentUserId());

        emAndamentoTasks.forEach(task -> task.setUser(currentUser));

        return vacationRepository.save(VacationMapper.map(request, originUser));

    }

    @Scheduled(cron = SCHEDULED_CHANGE_USER_OF_ENDED_VACATION)
    public void changeUserOfEndedVacation(){

        vacationRepository.findAllByEndDate(LocalDate.now())
                .stream().forEach(vacation -> taskService
                        .findTaskById(vacation.getTaskId())
                        .setUser(userService.findUserById(vacation.getOriginUser().getIdUser())));

    }

}
