package com.sergio.technical_test.domain.persistance;

import com.sergio.technical_test.domain.model.entity.Patient;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @EntityGraph(attributePaths = {"person"})
    Optional<Patient> findWithRelationsById(Long id);

    @Query("SELECT p FROM Patient p JOIN User u ON u.person = p.person WHERE u.id = :userId")
    Optional<Patient> findByUserId(@Param("userId") Long userId);
}
