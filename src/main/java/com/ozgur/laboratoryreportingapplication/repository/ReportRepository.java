package com.ozgur.laboratoryreportingapplication.repository;

import com.ozgur.laboratoryreportingapplication.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {

    Optional<Report> findByFileNumber(String fileNumber);

    @Query(value = "SELECT r FROM Report r LEFT JOIN r.user u")
    Page<Report> getReportsSortedByLaborant(Pageable pageable);

//    @Query("SELECT r FROM Report r WHERE r.dateOfReport BETWEEN :startDate AND :endDate AND " +
//            "(LOWER(r.someField) LIKE %:searchTerm% OR LOWER(r.anotherField) LIKE %:searchTerm%)")
//    Page<Report> searchReports(LocalDate startDate, LocalDate endDate, String searchTerm, Pageable pageable);
///////////////
}
