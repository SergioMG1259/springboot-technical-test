package com.sergio.technical_test.mapping;

import com.sergio.technical_test.domain.model.entity.Specialty;
import com.sergio.technical_test.dto.SpecialtyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyMapper {
    private final ModelMapper modelMapper;

    public SpecialtyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Specialty toEntity(SpecialtyDTO specialtyDTO) {
        return modelMapper.map(specialtyDTO, Specialty.class);
    }

    public SpecialtyDTO toDTO(Specialty specialty) {
        return modelMapper.map(specialty, SpecialtyDTO.class);
    }
}
