package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.VacationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface VacationRepository extends JpaRepository<VacationHistory, Long> {
    List<VacationHistory> findAllByEndDate(LocalDate localDate);
    @Query("SELECT v FROM VacationHistory v WHERE v.originUser.id = :originUserId")
    List<VacationHistory> findAllByOriginUser_Id(@Param("originUserId") UUID originUserId);

    List<VacationHistory> findAllByInitDate(LocalDate date);


}
