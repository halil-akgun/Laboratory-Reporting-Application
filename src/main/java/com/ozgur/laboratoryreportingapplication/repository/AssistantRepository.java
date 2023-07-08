package com.ozgur.laboratoryreportingapplication.repository;

import com.ozgur.laboratoryreportingapplication.entity.concretes.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssistantRepository extends JpaRepository<Assistant, Long> {
    Optional<Assistant> findByUsername(String username);

    Optional<Assistant> findByHospitalIdNumber(String hospitalIdNumber);
}
