package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @ManyToOne
    @JoinColumn(name = "originUserId")
    private User originUser;

    private UUID currentUserId;

    private Long taskId;

    @Column(nullable = false)
    private LocalDate initDate;

    @Column(nullable = false)
    private LocalDate endDate;


}
