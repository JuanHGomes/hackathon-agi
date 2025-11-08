package com.example.demo.service;

import com.example.demo.dto.request.TaskRequest;


import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.enums.Status;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public Task create(TaskRequest request) {

            return taskRepository.save(Task.builder()
                    .title(request.title())
                    .description(request.description())
                    .status(Status.PENDENTE)
                    .build());

    }


//    public void deleteTask(Long id){
//        Task task = taskRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
//
//        task.setDeleted(true);
//
//        taskRepository.save(task);
//    }

    public Task changeStatus(Long id, String status){

        Task task = findTaskById(id);

        task.setStatus(Status.valueOf(status.toUpperCase()));

       return taskRepository.save(task);

    }

    public Task setTaskUser(Long taskId, UUID userID){
        Task task = findTaskById(taskId);

        User user = userService.findUserById(userID);

        task.setUser(user);

        return taskRepository.save(task);
    }

    public Task findTaskById(Long id){

        return taskRepository.findById(id).orElseThrow(RuntimeException::new);

    }

    public List<Task> listAll(){

        return taskRepository.findAll();
    }


    public List<Task> listByUser(UUID userId){

        return taskRepository.findByUser_IdUser(userId);
    }

    public List<Task> listByUserAndStatusPending(UUID userId){

        List<Task> tasks = listByUser(userId);

        return tasks.stream()
                .filter(taskResponse -> taskResponse.getStatus() == Status.PENDENTE)
                .collect(Collectors.toList());
    }

    public List<Task> listEmAndamentoTasks(UUID userId){
        return taskRepository.buscarTasksPorUsuarioEStatus(userId, Status.EM_ANDAMENTO);
    }

    public List<Task> getUserTaskHistory(UUID userId) {
        return taskRepository.findTaskHistoryByUser(userId);
    }



}
