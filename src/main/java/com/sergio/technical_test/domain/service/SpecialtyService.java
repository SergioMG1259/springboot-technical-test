package com.sergio.technical_test.domain.service;

import com.sergio.technical_test.dto.SpecialtyDTO;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;

public interface SpecialtyService {
    SpecialtyDTO create(SpecialtyDTO specialtyDTO);
    SpecialtyDTO update(Long id, SpecialtyDTO specialtyDTO);
    void delete(Long id);
}
