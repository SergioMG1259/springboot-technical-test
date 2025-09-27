package com.sergio.technical_test.domain.service;

import com.sergio.technical_test.domain.model.entity.Employee;
import com.sergio.technical_test.domain.model.enums.Role;
import com.sergio.technical_test.dto.EmployeeCreateDTO;
import com.sergio.technical_test.dto.EmployeeResponseDTO;

public interface EmployeeService {
    EmployeeResponseDTO create(EmployeeCreateDTO employeeCreateDTO, Role role);
    Employee getById(Long id);
}
