package com.sergio.technical_test.service;

import com.sergio.technical_test.domain.model.entity.Employee;
import com.sergio.technical_test.domain.model.entity.Person;
import com.sergio.technical_test.domain.model.entity.Specialty;
import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.domain.model.enums.Role;
import com.sergio.technical_test.domain.persistance.EmployeeRepository;
import com.sergio.technical_test.domain.persistance.SpecialtyRepository;
import com.sergio.technical_test.domain.service.EmployeeService;
import com.sergio.technical_test.domain.service.PersonService;
import com.sergio.technical_test.domain.service.UserService;
import com.sergio.technical_test.dto.EmployeeCreateDTO;
import com.sergio.technical_test.dto.EmployeeResponseDTO;
import com.sergio.technical_test.dto.PersonRequestDTO;
import com.sergio.technical_test.dto.UserCreateDTO;
import com.sergio.technical_test.exceptions.BadRequestException;
import com.sergio.technical_test.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final PersonService personService;
    private final SpecialtyRepository specialtyRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserService userService, PersonService personService,
                               SpecialtyRepository specialtyRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
        this.personService = personService;
        this.specialtyRepository = specialtyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional()
    public EmployeeResponseDTO create(EmployeeCreateDTO employeeCreateDTO, Role role) {
        PersonRequestDTO personRequestDTO = new PersonRequestDTO(employeeCreateDTO.getPerson().getName(),
                employeeCreateDTO.getPerson().getSurname(), employeeCreateDTO.getPerson().getEmail(),
                employeeCreateDTO.getPerson().getBirthday());
        Person person = personService.create(personRequestDTO);

        UserCreateDTO userCreateDTO = new UserCreateDTO(employeeCreateDTO.getUser().getUserName(),
                passwordEncoder.encode(employeeCreateDTO.getUser().getPassword()));
        User user = userService.create(userCreateDTO, person);

        Employee employee = new Employee();
        employee.setPerson(person);
        employee.setRole(role);
        if (employee.getRole() == Role.DOCTOR) {
            if (employeeCreateDTO.getSpecialtyIds().isEmpty()) {
                throw new BadRequestException("Doctor must have at least one specialty");
            }
            Set<Specialty> specialties = new HashSet<>(specialtyRepository.findAllById(employeeCreateDTO.getSpecialtyIds()));
            if (specialties.isEmpty()) {
                throw new BadRequestException("Specialties do not exist");
            }
            employee.setSpecialties(specialties);
        } else {
            employee.setSpecialties(Collections.emptySet());
        }
        employeeRepository.save(employee);

        return new EmployeeResponseDTO(person.getName(), person.getSurname(), role);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getById(Long id) {
        return employeeRepository.findWithRelationsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public Employee getByUserId(Long id) {
        return employeeRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No employee associated with user id: " + id));
    }
}
