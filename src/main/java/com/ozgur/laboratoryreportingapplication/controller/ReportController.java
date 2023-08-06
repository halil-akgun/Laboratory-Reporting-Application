package com.ozgur.laboratoryreportingapplication.controller;

import com.ozgur.laboratoryreportingapplication.configuration.UserDetailsImpl;
import com.ozgur.laboratoryreportingapplication.service.ReportService;
import com.ozgur.laboratoryreportingapplication.shared.ReportResponse;
import com.ozgur.laboratoryreportingapplication.shared.ReportSaveUpdateRequest;
import com.ozgur.laboratoryreportingapplication.shared.ResponseMessage;
import com.ozgur.laboratoryreportingapplication.shared.UserResponse;
import com.ozgur.laboratoryreportingapplication.shared.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_LABORATORY_ASSISTANT')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage<ReportResponse> saveReport(@Valid @RequestBody ReportSaveUpdateRequest request) {
        return reportService.saveReport(request);
    }

    @GetMapping("getAllReports")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_LABORATORY_ASSISTANT')")
    Page<ReportResponse> getAllReports(Pageable pageable) {
        return reportService.getAllReports(pageable);
    }
}
