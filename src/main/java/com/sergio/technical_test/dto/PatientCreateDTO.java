package com.sergio.technical_test.dto;

import lombok.Data;

@Data
public class PatientCreateDTO {
    private PersonRequestDTO person;
    private UserCreateDTO user;
}
