package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class VacationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


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
    private LocalDateTime initDate;

    @Column(nullable = false)
    private LocalDateTime endDate;


}
