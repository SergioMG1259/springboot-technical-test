package com.sergio.technical_test.service;

import com.sergio.technical_test.domain.model.entity.Attention;
import com.sergio.technical_test.domain.model.entity.Employee;
import com.sergio.technical_test.domain.model.entity.Patient;
import com.sergio.technical_test.domain.persistance.AttentionRepository;
import com.sergio.technical_test.domain.service.AttentionService;
import com.sergio.technical_test.domain.service.EmployeeService;
import com.sergio.technical_test.domain.service.PatientService;
import com.sergio.technical_test.dto.*;
import com.sergio.technical_test.exceptions.BadRequestException;
import com.sergio.technical_test.exceptions.ResourceNotFoundException;
import com.sergio.technical_test.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AttentionServiceImpl implements AttentionService {
    private final AttentionRepository attentionRepository;
    private final PatientService patientService;
    private final EmployeeService employeeService;

    public AttentionServiceImpl(AttentionRepository attentionRepository, PatientService patientService,
                                EmployeeService employeeService) {
        this.attentionRepository = attentionRepository;
        this.patientService = patientService;
        this.employeeService = employeeService;
    }

    private AttentionResponseDTO getAttentionResponseDTO(Attention attentionCreated,
                                                                Patient patient, Employee employee) {
        AttentionResponseDTO attentionResponseDTO = new AttentionResponseDTO();
        attentionResponseDTO.setStartDate(attentionCreated.getStartDate());
        attentionResponseDTO.setEndDate(attentionCreated.getEndDate());
        attentionResponseDTO.setReason(attentionCreated.getReason());
        attentionResponseDTO.setCreatedAt(attentionCreated.getCreatedAt());
        attentionResponseDTO.setPatient(new PatientResponseDTO(patient.getPerson().getName(),
                patient.getPerson().getSurname(), patient.getRole()));
        attentionResponseDTO.setEmployee(new EmployeeResponseDTO(employee.getPerson().getName(),
                employee.getPerson().getSurname(), employee.getRole()));
        return attentionResponseDTO;
    }

    @Override
    @Transactional()
    public AttentionResponseDTO create(AttentionCreateDTO attentionCreateDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        Patient patient = patientService.getById(attentionCreateDTO.getPatientId());
        Employee employee;
        // En caso sea DOCTOR, no puede crear un attention para otro médico que no sea el mismo
        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"))) {
            employee = employeeService.getByUserId(principal.getUserId());
        } else {
            employee = employeeService.getById(attentionCreateDTO.getEmployeeId());
        }

        // Verifica que el paciente o el doctor no tengan citas ya agendadas en un rango de tiempo
        boolean overlap = attentionRepository.existsAttentionInRange(
                patient.getId(),
                employee.getId(),
                attentionCreateDTO.getStartDate(),
                attentionCreateDTO.getStartDate().plusMinutes(45)
        );
        if (overlap) {
            throw new BadRequestException("Either the patient or the employee already has an attention in that time range");
        }

        Attention attention = new Attention();
        attention.setStartDate(attentionCreateDTO.getStartDate());
        attention.setEndDate(attentionCreateDTO.getStartDate().plusMinutes(45));
        attention.setReason(attentionCreateDTO.getReason());
        attention.setEmployee(employee);
        attention.setPatient(patient);
        attention.setCreatedAt(LocalDateTime.now());
        Attention attentionCreated = attentionRepository.save(attention);
        return getAttentionResponseDTO(attentionCreated, patient, employee);
    }

    @Override
    @Transactional()
    public AttentionResponseDTO endAttention(Long id) {
        Attention attention = attentionRepository.findWithRelationsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attention not found with id: " + id));

        if (attention.getStartDate().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("To end an attention, the current date must be after the start date");
        }

        attention.setEndDate(LocalDateTime.now());
        attention.setUpdatedAt(LocalDateTime.now());
        return getAttentionResponseDTO(attention, attention.getPatient(),
                attention.getEmployee());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttentionResponseDTO> getAll(Pageable pageable) {
        return attentionRepository.findAll(pageable).map(
                attention -> getAttentionResponseDTO(attention, attention.getPatient(), attention.getEmployee()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttentionResponseDTO> getByPatient(Pageable pageable) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        Long userId = principal.getUserId();

        Patient patient = patientService.getByUserId(userId);
        return attentionRepository.findAllByPatient_Id(patient.getId(), pageable).map(
                attention -> getAttentionResponseDTO(attention, attention.getPatient(), attention.getEmployee()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttentionResponseDTO> getByEmployee(Pageable pageable) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        Long userId = principal.getUserId();

        Employee employee = employeeService.getByUserId(userId);
        return attentionRepository.findAllByEmployee_Id(employee.getId(), pageable).map(
                attention -> getAttentionResponseDTO(attention, attention.getPatient(), attention.getEmployee()));
    }

    @Override
    @Transactional()
    public AttentionResponseDTO update(Long id, AttentionUpdateDTO attentionUpdateDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        Attention attention = attentionRepository.findWithRelationsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attention not found with id: " + id));

        // Los ADMINs pueden modificar attentions de distintos DOCTORs
        // Los DOCTORs solo pueden modificar los attentions que les corresponden, no de otros DOCTORs
        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"))) {
            Employee employee = employeeService.getByUserId(principal.getUserId());
            if (!Objects.equals(attention.getEmployee().getId(), employee.getId())) {
                throw new AccessDeniedException("You cannot modify this attention");
            }
        }

        // End date debe ser posterior a start date
        if (attentionUpdateDTO.getEndDate().isBefore(attentionUpdateDTO.getStartDate())) {
            throw new BadRequestException("End date must be after start date");
        }

        // Verifica que el paciente o el doctor no tengan citas ya agendadas en un rango de tiempo (no toma en cuenta el mismo attention)
        boolean overlap = attentionRepository.existsAttentionInRangeExcludingId(
                attention.getPatient().getId(),
                attention.getEmployee().getId(),
                attentionUpdateDTO.getStartDate(),
                attentionUpdateDTO.getEndDate(),
                attention.getId()
        );
        if (overlap) {
            throw new BadRequestException("Either the patient or the employee already has an attention in that time range");
        }

        attention.setStartDate(attentionUpdateDTO.getStartDate());
        attention.setEndDate(attentionUpdateDTO.getEndDate());
        attention.setReason(attentionUpdateDTO.getReason());
        attention.setUpdatedAt(LocalDateTime.now());
        return getAttentionResponseDTO(attention, attention.getPatient(), attention.getEmployee());
    }

    @Override
    @Transactional()
    public void delete(Long id) {
        Attention attention = attentionRepository.findWithRelationsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attention not found with id: " + id));
        attentionRepository.delete(attention);
    }
}
