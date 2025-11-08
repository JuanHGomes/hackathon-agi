package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_vacation_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VacationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany
    @JoinColumn(name = "originUserId")
    private User originUser;


    @OneToMany
    @JoinColumn(name = "currentUserId")
    private User currentUser;

    @OneToMany
    @JoinColumn(name = "taskId")
    private Task task;

    @Column(nullable = false)
    private LocalDate initDate;

    @Column(nullable = false)
    private LocalDate endDate;


}
