package com.sergio.technical_test.domain.service;

import com.sergio.technical_test.dto.AttentionCreateDTO;
import com.sergio.technical_test.dto.AttentionResponseDTO;
import com.sergio.technical_test.dto.AttentionUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttentionService {
    AttentionResponseDTO create(AttentionCreateDTO attentionCreateDTO);
    AttentionResponseDTO endAttention(Long id);
    Page<AttentionResponseDTO> getAll(Pageable pageable);
    Page<AttentionResponseDTO> getByPatient(Pageable pageable);
    Page<AttentionResponseDTO> getByEmployee(Pageable pageable);
    AttentionResponseDTO update(Long id, AttentionUpdateDTO attentionUpdateDTO);
    void delete(Long id);
}
