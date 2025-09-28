package com.sergio.technical_test.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttentionUpdateDTO {
    @NotNull(message = "The startDate cannot be null")
    private LocalDateTime startDate;

    @NotNull(message = "The endDate cannot be null")
    private LocalDateTime endDate;

    @NotNull(message = "The reason cannot be null")
    @NotBlank(message = "The reason cannot be empty")
    @Size(max = 200, message = "The reason cannot exceed 200 characters")
    private String reason;
}
