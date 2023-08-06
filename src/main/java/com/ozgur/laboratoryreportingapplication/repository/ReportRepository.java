package com.ozgur.laboratoryreportingapplication.repository;

import com.ozgur.laboratoryreportingapplication.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByFileNumber(String fileNumber);
}
