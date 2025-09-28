package com.sergio.technical_test.domain.service;

import com.sergio.technical_test.domain.model.entity.Patient;
import com.sergio.technical_test.dto.PatientCreateDTO;
import com.sergio.technical_test.dto.PatientResponseDTO;

public interface PatientService {
    PatientResponseDTO create(PatientCreateDTO patientCreateDTO);
    Patient getById(Long id);
    Patient getByUserId(Long id);
}
