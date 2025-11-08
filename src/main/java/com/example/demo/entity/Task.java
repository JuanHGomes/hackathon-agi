package com.example.demo.entity;

import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tb_task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "originUserId")
    private User originUser;

    @ManyToOne
    @JoinColumn(name = "currentUserId")
    private User currentUser;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted = false;
}
