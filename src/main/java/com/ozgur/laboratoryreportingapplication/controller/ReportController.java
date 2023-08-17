package com.ozgur.laboratoryreportingapplication.controller;

import com.ozgur.laboratoryreportingapplication.service.ReportService;
import com.ozgur.laboratoryreportingapplication.shared.ReportResponse;
import com.ozgur.laboratoryreportingapplication.shared.ReportSaveRequest;
import com.ozgur.laboratoryreportingapplication.shared.ReportUpdateRequest;
import com.ozgur.laboratoryreportingapplication.shared.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseMessage<ReportResponse> saveReport(@Valid @RequestBody ReportSaveRequest request) {
        return reportService.saveReport(request);
    }

    @GetMapping("getAllReports")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_LABORATORY_ASSISTANT')")
    Page<ReportResponse> getAllReports(Pageable pageable, @RequestParam(name = "myReports") String myReports) {
        return reportService.getAllReports(pageable, myReports);
    }

    @GetMapping("searchInReports")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_LABORATORY_ASSISTANT')")
    Page<ReportResponse> searchInReports(
            Pageable pageable,
            @RequestParam(name = "searchTerm") String searchTerm,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate,
            @RequestParam(name = "myReports") String myReports
    ) {
        return reportService.searchInReports(pageable, searchTerm.trim(), startDate, endDate, myReports);
    }

    @DeleteMapping("/delete/{id:[0-9]+}")
    @PreAuthorize("@reportSecurityService.isAllowedToDelete(#id, principal)")
    public ResponseMessage<?> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return new ResponseMessage<>(null, "Report deleted.", HttpStatus.OK);
    }

    @GetMapping("getReport/{id:[0-9]+}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_LABORATORY_ASSISTANT')")
    ReportResponse getReportById(@PathVariable Long id) {
        return reportService.getReportById(id);
    }

    @PutMapping("updateReport/{id:[0-9]+}")
    @PreAuthorize("@reportSecurityService.isAllowedToUpdate(#id, principal)")
    ResponseEntity<?> updateReport(@PathVariable Long id, @RequestParam boolean removeImage,
                                   @Valid @RequestBody ReportUpdateRequest request) {
        return reportService.updateReport(id, removeImage, request);
    }
}
