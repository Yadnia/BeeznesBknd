package com.Beeznes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username ;

    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private Status status;

//    public User() {
//    }
//
//    public User(String username, Status status) {
//        this.username = username;
//        this.status = status;
//    }
}
