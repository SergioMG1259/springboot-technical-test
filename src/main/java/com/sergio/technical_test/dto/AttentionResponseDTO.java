package com.sergio.technical_test.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttentionResponseDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reason;
    private PatientResponseDTO patient;
    private EmployeeResponseDTO employee;
    private LocalDateTime createdDate;
}
