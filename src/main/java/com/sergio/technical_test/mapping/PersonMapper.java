package com.sergio.technical_test.mapping;

import com.sergio.technical_test.domain.model.entity.Person;
import com.sergio.technical_test.dto.PersonRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    private final ModelMapper modelMapper;

    public PersonMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Person toEntity(PersonRequestDTO personRequestDTO) {
        return modelMapper.map(personRequestDTO, Person.class);
    }
}
