package com.ozgur.laboratoryreportingapplication.repository;

import com.ozgur.laboratoryreportingapplication.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
