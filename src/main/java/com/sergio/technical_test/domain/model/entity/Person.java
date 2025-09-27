package com.sergio.technical_test.domain.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDateTime birthday;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private User user;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Employee employee;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Patient patient;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
