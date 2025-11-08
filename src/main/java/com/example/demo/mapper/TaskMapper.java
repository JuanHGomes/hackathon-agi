package com.example.demo.mapper;

import com.example.demo.dto.request.TaskRequest;
import com.example.demo.dto.response.TaskResponse;
import com.example.demo.entity.Task;
import com.example.demo.enums.Status;

public class TaskMapper {

    public static Task map(final TaskRequest request){
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .status(Status.PENDENTE)
                .build();
    }

    public static TaskResponse toResponse(final Task task){
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getOriginUser().getIdUser()
        );
    }
}
