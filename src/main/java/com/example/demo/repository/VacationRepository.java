package com.example.demo.repository;

import com.example.demo.entity.VacationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacationRepository extends JpaRepository<VacationHistory, Long> {
}
