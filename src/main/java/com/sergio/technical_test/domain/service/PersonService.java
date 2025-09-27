package com.sergio.technical_test.domain.service;

import com.sergio.technical_test.domain.model.entity.Person;
import com.sergio.technical_test.dto.PersonRequestDTO;

public interface PersonService {
    Person create(PersonRequestDTO personRequestDTO);
}