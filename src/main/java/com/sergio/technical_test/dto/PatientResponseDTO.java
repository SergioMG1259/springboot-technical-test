package com.sergio.technical_test.dto;

import com.sergio.technical_test.domain.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientResponseDTO {
    String name;
    String surname;
    Role role;
}
