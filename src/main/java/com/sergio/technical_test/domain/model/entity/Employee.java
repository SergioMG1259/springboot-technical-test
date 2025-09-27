package com.sergio.technical_test.domain.model.entity;

import com.sergio.technical_test.domain.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, foreignKey = @ForeignKey(name = "FK_person_employee_id"))
    private Person person;

    @ManyToMany // Solo para role Doctor
    @JoinTable(
            name = "doctor_specialty",
            joinColumns = @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "FK_doctor_specialty_employee_id")),
            inverseJoinColumns = @JoinColumn(name = "specialty_id", foreignKey = @ForeignKey(name = "FK_doctor_specialty_specialty"))
    )
    private Set<Specialty> specialties = new HashSet<>();
}
