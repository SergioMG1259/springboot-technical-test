package com.sergio.technical_test.service;

import com.sergio.technical_test.domain.model.entity.Specialty;
import com.sergio.technical_test.domain.persistance.SpecialtyRepository;
import com.sergio.technical_test.domain.service.SpecialtyService;
import com.sergio.technical_test.dto.SpecialtyDTO;
import com.sergio.technical_test.exceptions.BadRequestException;
import com.sergio.technical_test.exceptions.ResourceNotFoundException;
import com.sergio.technical_test.mapping.SpecialtyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository, SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.specialtyMapper = specialtyMapper;
    }

    @Override
    @Transactional()
    public SpecialtyDTO create(SpecialtyDTO specialtyDTO) {
        if (specialtyRepository.existsByName(specialtyDTO.getName())) {
            throw new BadRequestException("Specialty already exists");
        }
        Specialty specialty = specialtyMapper.toEntity(specialtyDTO);
        specialty.setCreatedAt(LocalDateTime.now());
        return specialtyMapper.toDTO(specialtyRepository.save(specialty));
    }

    @Override
    @Transactional()
    public SpecialtyDTO update(Long id, SpecialtyDTO specialtyDTO) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with id: " + id));

        specialty.setName(specialtyDTO.getName());
        specialty.setUpdatedAt(LocalDateTime.now());
        return specialtyMapper.toDTO(specialtyRepository.save(specialty));
    }

    @Override
    @Transactional()
    public void delete(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialty not found with id: " + id));

        specialtyRepository.delete(specialty);
    }
}
