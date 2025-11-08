package com.example.demo.controller;


import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    public final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask (@RequestBody TaskRequest taskRequest){
        TaskResponse newTask = taskService.create(taskRequest);

        return ResponseEntity.ok(newTask);
    }
}
