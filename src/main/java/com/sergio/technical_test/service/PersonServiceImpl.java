package com.sergio.technical_test.service;

import com.sergio.technical_test.domain.model.entity.Person;
import com.sergio.technical_test.domain.persistance.PersonRepository;
import com.sergio.technical_test.domain.service.PersonService;
import com.sergio.technical_test.dto.PersonRequestDTO;
import com.sergio.technical_test.exceptions.BadRequestException;
import com.sergio.technical_test.exceptions.ResourceNotFoundException;
import com.sergio.technical_test.mapping.PersonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Override
    @Transactional()
    public Person create(PersonRequestDTO personRequestDTO) {
        if (personRepository.existsByEmail(personRequestDTO.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        Person person = personMapper.toEntity(personRequestDTO);
        person.setCreatedAt(LocalDateTime.now());
        return personRepository.save(person);
    }

    @Override
    @Transactional()
    public Person update(Long id, PersonRequestDTO personRequestDTO) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        if (personRepository.existsByEmailAndIdNot(personRequestDTO.getEmail(), id)) {
            throw new BadRequestException("Email already exists");
        }

        person.setName(personRequestDTO.getName());
        person.setSurname(personRequestDTO.getSurname());
        person.setEmail(personRequestDTO.getEmail());
        person.setBirthday(personRequestDTO.getBirthday());
        person.setUpdatedAt(LocalDateTime.now());
        return personRepository.save(person);
    }
}
