package com.example.demo.service;

import com.example.demo.dto.request.VacationRequest;
import com.example.demo.dto.response.VacationHistoryResponse;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.entity.VacationHistory;
import com.example.demo.enums.Status;
import com.example.demo.mapper.VacationMapper;
import com.example.demo.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class VacationHistoryService {

    private final String SCHEDULED_CHANGE_USER_OF_ENDED_VACATION = "0 0 0 * * ?";

    private final TaskService taskService;
    private final UserService userService;
    private final VacationRepository vacationRepository;

    public List<Task> newVacationHistory(VacationRequest request) {

        User originUser = userService.findUserById(request.originUserId());
        User currentUser = userService.findUserById(request.currentUserId());

        List<Task> emAndamentoTasks = taskService.listEmAndamentoTasks(request.originUserId());

        emAndamentoTasks.forEach(task -> {

            task.setUser(currentUser);
            taskService.saveTask(task);

            vacationRepository.save(new VacationHistory(
                    originUser,
                    currentUser.getIdUser(),
                    task.getId(),
                    request.initDate(),
                    request.endDate()
            ));
        });

        return emAndamentoTasks;
    }

    @Scheduled(cron = "* * * * * *")
    public void changeUserOfEndedVacation(){

        log.info("Iniciando reatribuição de tarefas de férias finalizadas.");


        vacationRepository.findAllByEndDate(LocalDate.now())
                .forEach(vacation -> {

                   Task task = taskService.findTaskById(vacation.getTaskId());

                        if(task.getStatus() == Status.EM_ANDAMENTO){
                            task.setUser(vacation.getOriginUser());
                            taskService.saveTask(task);
                        }
                    });


    }

    @Scheduled(cron = "0 */2 * * * ?")
    private void transferOwnershipForUpcomingVacations() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        log.info("Iniciando transferência de titularidade para tarefas de férias que começam amanhã ({}).", tomorrow);


        vacationRepository.findAllByInitDate(tomorrow)
                .forEach(vacation -> {
                    Task task = taskService.findTaskById(vacation.getTaskId());

                    if (task.getStatus() == Status.EM_ANDAMENTO) {
                        task.setUser(userService.findUserById(vacation.getCurrentUserId()));
                        taskService.saveTask(task);
                    }
                });
    }



}


