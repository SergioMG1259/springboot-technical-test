package com.sergio.technical_test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PersonRequestDTO {
    @NotNull(message = "The name cannot be null")
    @NotBlank(message = "The name cannot be empty")
    @Size(max = 50, message = "The name cannot exceed 50 characters")
    private String name;

    @NotNull(message = "The surname cannot be null")
    @NotBlank(message = "The surname cannot be empty")
    @Size(max = 50, message = "The surname cannot exceed 50 characters")
    private String surname;

    @NotNull(message = "The email cannot be null")
    @NotBlank(message = "The email cannot be empty")
    @Email(message = "The email must be a valid email address")
    private String email;

    @NotNull(message = "The email cannot be null")
    @Past(message = "The birthday must be in the past")
    private LocalDateTime birthday;
}
