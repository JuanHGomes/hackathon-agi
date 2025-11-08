package com.example.demo.service;

import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.RegisterResponse;
import com.example.demo.dto.response.TaskResponse;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.enums.Status;
import com.example.demo.enums.Type;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final User user;
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

    public TaskResponse changeStatus(Long id, Status status){

        Task task = findTaskById(id);

        task.setStatus(status);

       return TaskMapper.toResponse(taskRepository.save(task));

    }

    public Task setTaskUser(Long taskId, UUID userID){
        Task task = findTaskById(taskId);

        User user = userService.findUserById(userID);

        task.setOriginUser(user);

        return taskRepository.save(task);
    }

    public Task findTaskById(Long id){

        return taskRepository.findById(id).orElseThrow(RuntimeException::new);

    }

    public List<Task> listAll(){

        return taskRepository.findAll();
    }


    public List<Task> listByUser(UUID userId){

        return taskRepository.findByOriginUser_IdUser(userId);
    }

//    public List<Task> listByUserAndStatusPending(UUID userId){
//
//        List<Task> tasks = taskRepository.findByCurrentUser_IdUser(userId);
//
//        return tasks.stream()
//                .filter(taskResponse -> taskResponse.getStatus() == Status.PENDENTE)
//                .collect(Collectors.toList());
//    }


}
