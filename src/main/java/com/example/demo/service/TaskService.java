package com.example.demo.service;

import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.TaskResponse;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.enums.Status;
import com.example.demo.mapper.TaskMapper;
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

    public TaskResponse create(TaskRequest request) {

        Task newTask = TaskMapper.map(request);

        taskRepository.save(newTask);

        return TaskMapper.toResponse(newTask);
    }


    public void deleteTask(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        task.setDeleted(true);

        taskRepository.save(task);
    }

    public TaskResponse changeStatus(Long id, Status status){

        Task task = findTaskById(id);

        task.setStatus(status);

       return TaskMapper.toResponse(taskRepository.save(task));

    }

    public TaskResponse takeTask(Long taskId, UUID userID){
        Task task = findTaskById(taskId);

        User user = userService.findById(userID); //CAROL CRIA ESSE METODO EM NOME DE JESUS

        task.setUser(user);

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public Task findTaskById(Long id){

        return taskRepository.findById(id).orElseThrow(RuntimeException::new);

    }

    public List<TaskResponse> listAll(){
        List<Task> tasks = taskRepository.findByDeletedFalse();
        return tasks.stream()
                .map(TaskMapper::toResponse)
                .collect(Collectors.toList());
    }


    public List<TaskResponse> listByUser(UUID userId){
        List<Task> tasks = taskRepository.findByUser_IdUser(userId);
        return tasks.stream()
                .map(TaskMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> listByUserAndStatusPending(UUID userId){

        List<Task> tasks = taskRepository.findByUser_IdUser(userId);

        return tasks.stream()
                .map(TaskMapper::toResponse)
                .filter(taskResponse -> taskResponse.status() == Status.PENDENTE)
                .collect(Collectors.toList());
    }


}
