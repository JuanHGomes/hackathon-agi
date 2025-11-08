package com.example.demo.mapper;

import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.enums.Status;

import java.util.Optional;
import java.util.UUID;

public class TaskMapper {

    public static Task map(final TaskRequest request, User user){
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .user(user)
                .status(Status.PENDENTE)
                .build();
    }

    public static TaskResponse toResponse(final Task task){
        final UUID userId = task.getUser() != null ? task.getUser().getIdUser() : null;
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                userId
        );
    }

}
