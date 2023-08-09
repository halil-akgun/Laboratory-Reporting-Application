package com.ozgur.laboratoryreportingapplication.repository;

import com.ozgur.laboratoryreportingapplication.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByFileNumber(String fileNumber);

    @Query(value = "SELECT r FROM Report r INNER JOIN r.user u",
            countQuery = "SELECT count(r.id) FROM Report r INNER JOIN r.user u")
    Page<Report> getReportsSortedByLaborant(Pageable pageable);

}
