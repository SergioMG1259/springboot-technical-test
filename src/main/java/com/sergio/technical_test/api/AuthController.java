package com.sergio.technical_test.api;

import com.sergio.technical_test.domain.model.enums.Role;
import com.sergio.technical_test.domain.service.EmployeeService;
import com.sergio.technical_test.domain.service.PatientService;
import com.sergio.technical_test.domain.service.UserService;
import com.sergio.technical_test.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PatientService patientService;
    private final EmployeeService employeeService;
    private final UserService userService;

    public AuthController(PatientService patientService, EmployeeService employeeService, UserService userService) {
        this.patientService = patientService;
        this.employeeService = employeeService;
        this.userService = userService;
    }

    // Cualquiera puede crearse una cuenta como paciente
    @PostMapping("/createPatient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientCreateDTO patientCreateDTO) {
        PatientResponseDTO patientResponseDTO = patientService.create(patientCreateDTO);
        return new ResponseEntity<PatientResponseDTO>(patientResponseDTO, HttpStatus.CREATED);
    }

    // Solo un admin puede crear otro admin
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createAdmin")
    public ResponseEntity<EmployeeResponseDTO> createAdmin(@Valid @RequestBody EmployeeCreateDTO employeeCreateDTO) {
        EmployeeResponseDTO patientResponseDTO = employeeService.create(employeeCreateDTO, Role.ADMIN);
        return new ResponseEntity<EmployeeResponseDTO>(patientResponseDTO, HttpStatus.CREATED);
    }

    // Solo un admin puede crear un doctor
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createDoctor")
    public ResponseEntity<EmployeeResponseDTO> createDoctor(@Valid @RequestBody EmployeeCreateDTO employeeCreateDTO) {
        EmployeeResponseDTO patientResponseDTO = employeeService.create(employeeCreateDTO, Role.DOCTOR);
        return new ResponseEntity<EmployeeResponseDTO>(patientResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = userService.login(loginRequestDTO);
        return new ResponseEntity<LoginResponseDTO>(loginResponseDTO, HttpStatus.OK);
    }
}
