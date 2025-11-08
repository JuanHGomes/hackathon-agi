package com.example.demo.controller;


import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.entity.Task;
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
    public ResponseEntity<Task> createTask (@RequestBody TaskRequest taskRequest){
        Task newTask = taskService.create(taskRequest);

        return ResponseEntity.ok(newTask);
    }

    @PostMapping("/setUser/{idUser}/{idTask}")
    public ResponseEntity<Task> createTask (@PathVariable UUID idUser, @PathVariable Long idTask){

        return ResponseEntity.ok(taskService.setTaskUser(idTask, idUser));
    }

    @GetMapping()
    public List<Task> listTasks(){
        return taskService.listAll();
    }

    @GetMapping("/listByUser/{userId}")
    public List<Task> listTasks(@PathVariable UUID userId){
        return taskService.listByUser(userId);
    }




}
