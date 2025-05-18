package com.iktproject.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String surname;

    private Double gradeAverage;

    private int takenTests;

    private String password;

    @Enumerated(EnumType.STRING)
    private Type type;

    public User(String username, String name, String surname, Double gradeAverage, int takenTests, String password, Type type) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.takenTests = takenTests;
        this.gradeAverage = gradeAverage;
        this.password = password;
        this.type = type;
    }

    public User() {
    }
}