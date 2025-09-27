package com.sergio.technical_test.service;

import com.sergio.technical_test.domain.model.entity.Employee;
import com.sergio.technical_test.domain.model.entity.Person;
import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.domain.model.enums.Role;
import com.sergio.technical_test.domain.persistance.EmployeeRepository;
import com.sergio.technical_test.domain.service.EmployeeService;
import com.sergio.technical_test.domain.service.PersonService;
import com.sergio.technical_test.domain.service.UserService;
import com.sergio.technical_test.dto.EmployeeCreateDTO;
import com.sergio.technical_test.dto.EmployeeResponseDTO;
import com.sergio.technical_test.dto.PersonRequestDTO;
import com.sergio.technical_test.dto.UserCreateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final PersonService personService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserService userService, PersonService personService) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
        this.personService = personService;
    }

    @Override
    @Transactional()
    public EmployeeResponseDTO create(EmployeeCreateDTO employeeCreateDTO, Role role) {
        PersonRequestDTO personRequestDTO = new PersonRequestDTO(employeeCreateDTO.getPerson().getName(),
                employeeCreateDTO.getPerson().getSurname(), employeeCreateDTO.getPerson().getEmail(),
                employeeCreateDTO.getPerson().getBirthday());
        Person person = personService.create(personRequestDTO);

        UserCreateDTO userCreateDTO = new UserCreateDTO(employeeCreateDTO.getUser().getUserName(),
                employeeCreateDTO.getUser().getPassword());
        User user = userService.create(userCreateDTO, person);

        Employee employee = new Employee();
        employee.setPerson(person);
        employee.setRole(role);
        employeeRepository.save(employee);
        return new EmployeeResponseDTO(person.getName(), person.getSurname(), person.getEmail(), user.getUserName(), role);
    }
}
