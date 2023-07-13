package com.ozgur.laboratoryreportingapplication.repository;

import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.entity.UserRole;
import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByHospitalIdNumber(String hospitalIdNumber);

    boolean existsByUserRole(UserRole userRole);
}
