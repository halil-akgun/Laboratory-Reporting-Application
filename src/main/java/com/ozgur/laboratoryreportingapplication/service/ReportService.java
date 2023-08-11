package com.ozgur.laboratoryreportingapplication.service;

import com.ozgur.laboratoryreportingapplication.entity.Report;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.repository.ReportRepository;
import com.ozgur.laboratoryreportingapplication.shared.ReportResponse;
import com.ozgur.laboratoryreportingapplication.shared.ReportSaveUpdateRequest;
import com.ozgur.laboratoryreportingapplication.shared.ResponseMessage;
import com.ozgur.laboratoryreportingapplication.utils.FileService;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;
    private final Mapper mapper;
    private final FileService fileService;

    public ResponseMessage<ReportResponse> saveReport(ReportSaveUpdateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserPojoWithUsername(username);
        Report report = mapper.createReportFromReportSaveUpdateRequest(request);
        report.setUser(user);
        if (request.getImageOfReport() != null) {
            try {
                String imageName = fileService.writeBase64EncodedStringToFileForReportPicture(request.getImageOfReport());
                report.setImageOfReport(imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Report savedReport = reportRepository.save(report);

        return ResponseMessage.<ReportResponse>builder()
                .message("Report saved successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(mapper.createReportResponseFromReport(savedReport)).build();
    }

    public void saveDummyReports(ReportSaveUpdateRequest request, User user) {
        Report report = mapper.createReportFromReportSaveUpdateRequest(request);
        report.setUser(user);
        reportRepository.save(report);
    }

    public Page<ReportResponse> getAllReports(Pageable pageable) {

        if (!pageable.getSort().toString().contains("laborant"))
            return reportRepository.findAll(pageable).map(mapper::createReportResponseFromReport);

        if (pageable.getSort().toString().contains("ASC"))
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by("user.fullName", "id").ascending());//id: r.id (report.id)
        else
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by("user.fullName", "id").descending());

//        When you don't give the second parameter (id), the records on the first 2 pages are the same.

        return reportRepository.getReportsSortedByLaborant(pageable).map(mapper::createReportResponseFromReport);
    }

    public Page<ReportResponse> searchInReports(Pageable pageable, String searchTerm, String startDate, String endDate) {

        if (pageable.getSort().toString().contains("laborant")) {
            if (pageable.getSort().toString().contains("ASC"))
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("user.name", "id").ascending());
            else
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("user.name", "id").descending());
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate convertedStartDate = null;
        LocalDate convertedEndDate = null;

        if (startDate != null && !startDate.isEmpty()) {
            try {
                convertedStartDate = LocalDate.parse(startDate, dateFormatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        if (endDate != null && !endDate.isEmpty()) {
            try {
                convertedEndDate = LocalDate.parse(endDate, dateFormatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        Specification<Report> spec = Specification.where(null);

        if (convertedStartDate != null && convertedEndDate != null) {
            LocalDate finalConvertedStartDate = convertedStartDate; // for Lambda
            LocalDate finalConvertedEndDate = convertedEndDate;
            spec = spec.and((root, query, builder) ->
                    builder.between(root.get("dateOfReport"), finalConvertedStartDate, finalConvertedEndDate)
            );
        } else if (convertedStartDate == null && convertedEndDate != null) {
            LocalDate finalConvertedEndDate1 = convertedEndDate;
            spec = spec.and((root, query, builder) ->
                    builder.lessThan(root.get("dateOfReport"), finalConvertedEndDate1)
            );
        } else if (convertedStartDate != null) {
            LocalDate finalConvertedStartDate1 = convertedStartDate;
            spec = spec.and((root, query, builder) ->
                    builder.greaterThan(root.get("dateOfReport"), finalConvertedStartDate1)
            );
        }

        if (searchTerm != null && !searchTerm.isEmpty()) {
            spec = spec.and((root, query, builder) ->
                    builder.or(
                            builder.like(builder.lower(root.get("fileNumber")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("patientName")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("patientSurname")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("patientIdNumber")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("diagnosisTitle")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("diagnosisDetails")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("user").get("fullName")), "%" + searchTerm.toLowerCase() + "%")
                    )
            );
        }
        return reportRepository.findAll(spec, pageable).map(mapper::createReportResponseFromReport);
    }
}
