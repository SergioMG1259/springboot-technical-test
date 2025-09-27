package com.sergio.technical_test.domain.persistance;

import com.sergio.technical_test.domain.model.entity.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @EntityGraph(attributePaths = {"person"})
    Optional<Employee> findWithRelationsById(Long id);
}
