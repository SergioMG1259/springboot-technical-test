package com.sergio.technical_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SpecialtyDTO {
    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be empty")
    @Size(max = 50, message = "The name cannot exceed 50 characters")
    private String name;
}
