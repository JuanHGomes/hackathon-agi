package com.example.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
public class HistoricoFerias {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @OneToMany
    @JoinColumn(name = "originUserId")
    private User originUser;


    @OneToMany
    @JoinColumn(name = "currentUserId")
    private User currentUser;

    @Column(nullable = false)
    @JoinColumn(name = "taskId")
    private Task task;

    @Column(nullable = false)
    private LocalDateTime initDate;

    @Column(nullable = false)
    private LocalDateTime endDate;


}
