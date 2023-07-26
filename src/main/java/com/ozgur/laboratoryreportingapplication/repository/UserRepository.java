package com.ozgur.laboratoryreportingapplication.repository;

import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.entity.UserRole;
import com.ozgur.laboratoryreportingapplication.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.DoubleStream;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByHospitalIdNumber(String hospitalIdNumber);

    boolean existsByUserRole(UserRole userRole);

    Page<User> findByUsernameNot(Pageable pageable, String username);

    boolean existsByUsername(String username);

    boolean existsByHospitalIdNumber(String hospitalIdNumber);
}
