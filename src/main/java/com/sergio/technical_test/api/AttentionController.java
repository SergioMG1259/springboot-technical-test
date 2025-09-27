package com.sergio.technical_test.api;

import com.sergio.technical_test.domain.service.AttentionService;
import com.sergio.technical_test.dto.AttentionCreateDTO;
import com.sergio.technical_test.dto.AttentionResponseDTO;
import com.sergio.technical_test.dto.AttentionUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attentions")
public class AttentionController {
    private final AttentionService attentionService;

    public AttentionController(AttentionService attentionService) {
        this.attentionService = attentionService;
    }

    @GetMapping
    public ResponseEntity<Page<AttentionResponseDTO>> getAllAttentions(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       @RequestParam(defaultValue = "DESC") String sortDirection) {
        // Por defecto se ordena de más nuevo a más antiguo (DESC)
        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by("startDate").ascending()
                : Sort.by("startDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return new ResponseEntity<Page<AttentionResponseDTO>>(attentionService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/me/patient")
    public ResponseEntity<Page<AttentionResponseDTO>> getAllAttentionsByPatient(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam(defaultValue = "DESC") String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by("startDate").ascending()
                : Sort.by("startDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return new ResponseEntity<Page<AttentionResponseDTO>>(attentionService.getByPatient(1L, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AttentionResponseDTO> createAttention(@Valid @RequestBody AttentionCreateDTO attentionCreateDTO) {
        AttentionResponseDTO attentionResponseDTO = attentionService.create(attentionCreateDTO);
        return new ResponseEntity<AttentionResponseDTO>(attentionResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{attentionId}")
    public ResponseEntity<AttentionResponseDTO> endAttention(@PathVariable("attentionId") Long attentionId) {
        AttentionResponseDTO attentionResponseDTO = attentionService.endAttention(attentionId);
        return new ResponseEntity<AttentionResponseDTO>(attentionResponseDTO, HttpStatus.OK);
    }

    @PutMapping("{attentionId}")
    public ResponseEntity<AttentionResponseDTO> updateAttention(@PathVariable("attentionId") Long attentionId,
                                                                @Valid @RequestBody AttentionUpdateDTO attentionUpdateDTO) {
        AttentionResponseDTO attentionResponseDTO = attentionService.update(attentionId, attentionUpdateDTO);
        return new ResponseEntity<AttentionResponseDTO>(attentionResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("{attentionId}")
    public ResponseEntity<?> deleteAttention(@PathVariable("attentionId") Long attentionId) {
        attentionService.delete(attentionId);
        return new ResponseEntity<AttentionResponseDTO>(HttpStatus.NO_CONTENT);
    }
}

