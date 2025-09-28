package com.sergio.technical_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotNull(message = "The username cannot be null")
    @NotBlank(message = "The username cannot be empty")
    @Size(max = 25, message = "The username cannot exceed 25 characters")
    private String userName;
    @NotNull(message = "The password cannot be null")
    @NotBlank(message = "The password cannot be empty")
    @Size(min = 8, max = 20, message = "The password must have a minimum of 8 characters and a maximum of 20 characters.")
    private String password;
}
