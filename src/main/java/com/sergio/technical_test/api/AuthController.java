package com.sergio.technical_test.api;

import com.sergio.technical_test.domain.model.enums.Role;
import com.sergio.technical_test.domain.service.EmployeeService;
import com.sergio.technical_test.domain.service.PatientService;
import com.sergio.technical_test.dto.EmployeeCreateDTO;
import com.sergio.technical_test.dto.EmployeeResponseDTO;
import com.sergio.technical_test.dto.PatientCreateDTO;
import com.sergio.technical_test.dto.PatientResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PatientService patientService;
    private final EmployeeService employeeService;

    public AuthController(PatientService patientService, EmployeeService employeeService) {
        this.patientService = patientService;
        this.employeeService = employeeService;
    }

    @PostMapping("/createPatient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientCreateDTO patientCreateDTO) {
        PatientResponseDTO patientResponseDTO = patientService.create(patientCreateDTO);
        return new ResponseEntity<PatientResponseDTO>(patientResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<EmployeeResponseDTO> createAdmin(@Valid @RequestBody EmployeeCreateDTO employeeCreateDTO) {
        EmployeeResponseDTO patientResponseDTO = employeeService.create(employeeCreateDTO, Role.ADMIN);
        return new ResponseEntity<EmployeeResponseDTO>(patientResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/createDoctor")
    public ResponseEntity<EmployeeResponseDTO> createDoctor(@Valid @RequestBody EmployeeCreateDTO employeeCreateDTO) {
        EmployeeResponseDTO patientResponseDTO = employeeService.create(employeeCreateDTO, Role.DOCTOR);
        return new ResponseEntity<EmployeeResponseDTO>(patientResponseDTO, HttpStatus.CREATED);
    }
}
