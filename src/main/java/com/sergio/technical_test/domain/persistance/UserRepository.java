package com.sergio.technical_test.domain.persistance;

import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.security.UserWithRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserName(String userName);

    @Query("""
        SELECT u as user, COALESCE(p.role, e.role) AS role, pe.email as email
        FROM User u
        JOIN u.person pe
        LEFT JOIN Patient p ON p.person = u.person
        LEFT JOIN Employee e ON e.person = u.person
        WHERE u.userName = :userName
    """)
    Optional<UserWithRole> findByUserName(@Param("userName") String userName);
}
