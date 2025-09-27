package com.sergio.technical_test.service;

import com.sergio.technical_test.domain.model.entity.Person;
import com.sergio.technical_test.domain.persistance.PersonRepository;
import com.sergio.technical_test.domain.service.PersonService;
import com.sergio.technical_test.dto.PersonRequestDTO;
import com.sergio.technical_test.exceptions.BadRequestException;
import com.sergio.technical_test.mapping.PersonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return personRepository.save(person);
    }
}
