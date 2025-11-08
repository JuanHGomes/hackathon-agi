package com.example.demo.entity;

import com.example.demo.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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

    @EqualsAndHashCode.Include
    @Column(unique = true, nullable = false)
    private UUID uuid;

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

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private final Set<Task> tasks = new HashSet<>();

    @PrePersist
    public void prePersist(){
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

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


    public void assignUserToTaks(Task task){
        if (task != null) {
            this.tasks.add(task);
            System.out.println("Tarefa " + task.getTitle() + " associada ao usuário '" + name + "'");
        }
    }

}
