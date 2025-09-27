package com.sergio.technical_test.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class EmployeeCreateDTO {
    private PersonRequestDTO person;
    private UserCreateDTO user;
    private Set<Long> specialtyIds = new HashSet<>();
}
