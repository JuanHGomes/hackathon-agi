package com.example.demo.entity;

import com.example.demo.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class User implements UserDetails {

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
    @Enumerated(EnumType.STRING)
    private Role role;

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

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role == null ? List.of() : List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
