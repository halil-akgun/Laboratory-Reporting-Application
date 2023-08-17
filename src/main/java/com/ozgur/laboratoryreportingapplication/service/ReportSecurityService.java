package com.ozgur.laboratoryreportingapplication.service;

import com.ozgur.laboratoryreportingapplication.configuration.UserDetailsImpl;
import com.ozgur.laboratoryreportingapplication.entity.Report;
import com.ozgur.laboratoryreportingapplication.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportSecurityService {

    private final ReportRepository reportRepository;

    public boolean isAllowedToDelete(Long id, UserDetailsImpl loggedInUser) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        return (optionalReport.filter(report -> Objects.equals(report.getUser().getId(), loggedInUser.getUser().getId())).isPresent()) ||
                (loggedInUser.getUser().getUsername().equals("admin"));
    }

    public boolean isAllowedToUpdate(Long id, UserDetailsImpl loggedInUser) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        return (optionalReport.filter(report -> Objects.equals(report.getUser().getId(), loggedInUser.getUser().getId())).isPresent()) ||
                (loggedInUser.getUser().getUsername().equals("admin"));
    }
}
