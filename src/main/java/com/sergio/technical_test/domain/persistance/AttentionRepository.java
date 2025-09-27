package com.sergio.technical_test.domain.persistance;

import com.sergio.technical_test.domain.model.entity.Attention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AttentionRepository extends JpaRepository<Attention, Long> {
    @EntityGraph(attributePaths = {"patient.person", "employee.person"})
    Optional<Attention> findWithRelationsById(Long id);

    @EntityGraph(attributePaths = {
            "patient",          // carga patient
            "patient.person",   // y también su person
            "employee",         // carga employee
            "employee.person"   // y también su person
    })
    Page<Attention> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {
            "patient",          // carga patient
            "patient.person",   // y también su person
            "employee",         // carga employee
            "employee.person"   // y también su person
    })
    Page<Attention> findAllByPatient_Id(Long id, Pageable pageable);

    @Query("""
    SELECT COUNT(a) > 0 FROM Attention a
    WHERE (a.patient.id = :patientId OR a.employee.id = :employeeId)
      AND a.startDate < :endDate
      AND a.endDate > :startDate
    """)
    boolean existsAttentionInRange(@Param("patientId") Long patientId,
                                   @Param("employeeId") Long employeeId,
                                   @Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);

    @Query("""
    SELECT COUNT(a) > 0 FROM Attention a
    WHERE (a.patient.id = :patientId OR a.employee.id = :employeeId)
      AND a.startDate < :endDate
      AND a.endDate > :startDate
      AND a.id <> :attentionId
    """)
    boolean existsAttentionInRangeExcludingId(@Param("patientId") Long patientId,
                                              @Param("employeeId") Long employeeId,
                                              @Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate,
                                              @Param("attentionId") Long attentionId);
}
