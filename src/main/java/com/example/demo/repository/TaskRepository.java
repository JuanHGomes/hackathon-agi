package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.user.id = :idUsuario AND t.status = :statusTarefa")
    List<Task> buscarTasksPorUsuarioEStatus(@Param("idUsuario") UUID idUsuario, @Param("statusTarefa") Status statusTarefa);

    List<Task> findByUser_IdUser(UUID idUser);

    @Override
    Optional<Task> findById(Long Long);


}
