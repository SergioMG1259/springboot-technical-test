package com.sergio.technical_test.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttentionCreateDTO {
    @NotNull(message = "The startDate cannot be null")
    @FutureOrPresent(message = "The start date must be in the present or future")
    private LocalDateTime startDate;

    @NotNull(message = "The reason cannot be null")
    @NotBlank(message = "The reason cannot be empty")
    @Size(max = 200, message = "The reason cannot exceed 200 characters")
    private String reason;

    @NotNull(message = "The patient id cannot be null")
    private Long patientId;

    @NotNull(message = "The employee id cannot be null")
    private Long employeeId;
}
