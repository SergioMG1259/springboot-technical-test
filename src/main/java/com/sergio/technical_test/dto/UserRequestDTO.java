package com.sergio.technical_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotNull(message = "The username cannot be null")
    @NotBlank(message = "The username cannot be empty")
    @Size(max = 25, message = "The username cannot exceed 25 characters")
    private String userName;
}
