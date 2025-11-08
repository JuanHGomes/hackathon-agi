package com.example.demo.controller;


import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    public final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask (@RequestBody TaskRequest taskRequest){

        return ResponseEntity.ok(TaskMapper.toResponse(taskService.create(taskRequest)));
    }

    @PostMapping("/setUser/{idUser}/{idTask}")
    public ResponseEntity<TaskResponse> setUser(@PathVariable UUID idUser, @PathVariable Long idTask){

        return ResponseEntity.ok(TaskMapper.toResponse(taskService.setTaskUser(idTask, idUser)));
    }

    @GetMapping()
    public List<TaskResponse> listTasks(){
        return taskService.listAll().stream().map(TaskMapper::toResponse).toList();
    }

//    @GetMapping("/historico/{userId}")
//    public List<Task> getUserTaskHistory(@PathVariable UUID userId) {
//        return taskService.getUserTaskHistory(userId);
//    }


    @GetMapping("/listByUserAndStatus/{userId}")
    public List<TaskResponse> listByUserAndStatus(@PathVariable UUID userId){
        return taskService.listByUserAndStatusPending(userId).stream().map(TaskMapper::toResponse).toList();
    }

    @PostMapping("/changeStatus/{idTask}")
    public TaskResponse changeStats(@PathVariable Long idTask, @RequestParam String status){
        return TaskMapper.toResponse(taskService.changeStatus(idTask, status));
    }

    @GetMapping("/tasksByUserEmAndamento/{userId}")
    public List<TaskResponse> tasksByUserSatausEmAndamento(@PathVariable UUID userId){
        return taskService.listEmAndamentoTasks(userId).stream().map(TaskMapper::toResponse).toList();
    }


}
