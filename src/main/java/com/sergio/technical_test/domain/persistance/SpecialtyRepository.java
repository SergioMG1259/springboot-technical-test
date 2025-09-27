package com.sergio.technical_test.domain.persistance;

import com.sergio.technical_test.domain.model.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    boolean existsByName(String name);

    @Modifying
    @Transactional()
    @Query(value = "DELETE FROM doctor_specialty WHERE specialty_id = :specialtyId", nativeQuery = true)
    void deleteRelationsBySpecialty(@Param("specialtyId") Long specialtyId);
}
