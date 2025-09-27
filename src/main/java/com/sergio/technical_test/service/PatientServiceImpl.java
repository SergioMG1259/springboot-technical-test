package com.sergio.technical_test.service;

import com.sergio.technical_test.domain.model.entity.Patient;
import com.sergio.technical_test.domain.model.entity.Person;
import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.domain.model.enums.Role;
import com.sergio.technical_test.domain.persistance.PatientRepository;
import com.sergio.technical_test.domain.service.PatientService;
import com.sergio.technical_test.domain.service.PersonService;
import com.sergio.technical_test.domain.service.UserService;
import com.sergio.technical_test.dto.PatientCreateDTO;
import com.sergio.technical_test.dto.PatientResponseDTO;
import com.sergio.technical_test.dto.PersonRequestDTO;
import com.sergio.technical_test.dto.UserCreateDTO;
import com.sergio.technical_test.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final UserService userService;
    private final PersonService personService;

    public PatientServiceImpl(PatientRepository patientRepository, UserService userService, PersonService personService) {
        this.patientRepository = patientRepository;
        this.userService = userService;
        this.personService = personService;
    }

    @Override
    @Transactional()
    public PatientResponseDTO create(PatientCreateDTO patientCreateDTO) {
        PersonRequestDTO personRequestDTO = new PersonRequestDTO(patientCreateDTO.getPerson().getName(),
                patientCreateDTO.getPerson().getSurname(), patientCreateDTO.getPerson().getEmail(),
                patientCreateDTO.getPerson().getBirthday());
        Person person = personService.create(personRequestDTO);

        UserCreateDTO userCreateDTO = new UserCreateDTO(patientCreateDTO.getUser().getUserName(),
                patientCreateDTO.getUser().getPassword());
        User user = userService.create(userCreateDTO, person);

        Patient patient = new Patient();
        patient.setPerson(person);
        patient.setRole(Role.PATIENT);
        patientRepository.save(patient);
        return new PatientResponseDTO(person.getName(), person.getSurname(), patient.getRole());
    }

    @Override
    @Transactional(readOnly = true)
    public Patient getById(Long id) {
        return patientRepository.findWithRelationsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }
}
