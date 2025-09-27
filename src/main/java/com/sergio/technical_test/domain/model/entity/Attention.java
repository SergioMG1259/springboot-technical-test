package com.sergio.technical_test.domain.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Attention implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDate; // Es la fecha de cuando será la atención

    @Column(nullable = false)
    private LocalDateTime endDate; // Es la fecha en la que se termina la atención, por defecto las citas duran 1 hora pero se modifican

    @Column(nullable = false, length = 200)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_patient_attention_id"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_employee_attention_id"))
    private Employee employee;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
