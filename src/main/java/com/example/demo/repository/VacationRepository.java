package com.example.demo.repository;

import com.example.demo.entity.VacationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VacationRepository extends JpaRepository<VacationHistory, Long> {
    List<VacationHistory> findAllByEndDate(LocalDate localDate);
}
