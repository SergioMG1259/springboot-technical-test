package com.sergio.technical_test.dto;

import com.sergio.technical_test.domain.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeResponseDTO {
    String name;
    String surname;
    String email;
    String userName;
    Role role;
}
