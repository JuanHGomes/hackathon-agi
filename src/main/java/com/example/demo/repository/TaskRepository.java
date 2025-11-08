package com.example.demo.repository;

import com.example.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByDeletedFalse();

    List<Task> findByOriginUser_IdUser(UUID idUser);
    List<Task> findByCurrentUser_IdUser(UUID idUser);
}
