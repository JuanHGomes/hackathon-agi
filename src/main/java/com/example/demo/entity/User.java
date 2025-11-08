package com.example.demo.entity;

import com.example.demo.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user")
    private UUID idUser;

    @Column(nullable = false)
    @ToString.Include
    private String name;

    @Column(nullable = false, unique = true)
    @ToString.Include
    private String email;

    @Column(nullable = false)
    @ToString.Exclude
    private String password;

    @Column(nullable = false)
    @ToString.Include
    private Type type;

    // Usuário criou essas tarefas
    @OneToMany(mappedBy = "originUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasksCreated = new HashSet<>();

//    // Usuário responsável atual dessas tarefas
//    @OneToMany(mappedBy = "currentUser", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Task> tasksResponsible = new HashSet<>();

    // Histórico de férias onde ele é o originador
    @OneToMany(mappedBy = "originUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VacationHistory> vacationsOrigin = new HashSet<>();

//    // Histórico de férias onde ele é o atual responsável
//    @OneToMany(mappedBy = "currentUser", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<VacationHistory> vacationsCurrent = new HashSet<>();

//    @OneToMany(mappedBy = "currentUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private final Set<Task> tasks = new HashSet<>();


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(Type type) {
        this.type = type;
    }


}
