package com.sergio.technical_test.api;

import com.sergio.technical_test.domain.service.SpecialtyService;
import com.sergio.technical_test.dto.SpecialtyDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/specialties")
// Solo admins pueden modificar specialty
@PreAuthorize("hasRole('ADMIN')")
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping
    public ResponseEntity<SpecialtyDTO> createSpecialty(@Valid @RequestBody SpecialtyDTO specialtyDTO) {
        SpecialtyDTO specialty = specialtyService.create(specialtyDTO);
        return new ResponseEntity<SpecialtyDTO>(specialty, HttpStatus.CREATED);
    }

    @PutMapping("/{specialtyId}")
    public ResponseEntity<SpecialtyDTO> updateSpecialty(@Valid @RequestBody SpecialtyDTO specialtyDTO,
                                                        @PathVariable("specialtyId") Long specialtyId) {
        SpecialtyDTO specialty = specialtyService.update(specialtyId, specialtyDTO);
        return new ResponseEntity<SpecialtyDTO>(specialty, HttpStatus.OK);
    }

    @DeleteMapping("/{specialtyId}")
    public ResponseEntity<?> deleteSpecialty(@PathVariable("specialtyId") Long specialtyId) {
        specialtyService.delete(specialtyId);
        return new ResponseEntity<SpecialtyDTO>(HttpStatus.NO_CONTENT);
    }
}
