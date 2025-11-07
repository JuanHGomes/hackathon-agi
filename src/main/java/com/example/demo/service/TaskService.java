package com.example.demo.service;

import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.TaskResponse;

import com.example.demo.entity.Task;
import com.example.demo.enums.Status;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse create(TaskRequest request){

        Task newTask = TaskMapper.map(request);

        taskRepository.save(newTask);

        return TaskMapper.toResponse(newTask);

    }
}
