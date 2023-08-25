package com.ozgur.laboratoryreportingapplication.service;

import com.ozgur.laboratoryreportingapplication.controller.UserController;
import com.ozgur.laboratoryreportingapplication.entity.Patient;
import com.ozgur.laboratoryreportingapplication.entity.Report;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.error.ResourceNotFoundException;
import com.ozgur.laboratoryreportingapplication.repository.PatientRepository;
import com.ozgur.laboratoryreportingapplication.repository.ReportRepository;
import com.ozgur.laboratoryreportingapplication.shared.*;
import com.ozgur.laboratoryreportingapplication.utils.FileService;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import com.ozgur.laboratoryreportingapplication.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final PatientRepository patientRepository;
    private final Mapper mapper;
    private final FileService fileService;
    Logger logger = LoggerFactory.getLogger(UserController.class);


    public ResponseMessage<ReportResponse> saveReport(ReportSaveRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserPojoWithUsername(username);

        Patient patient = patientRepository.findByIdNumber(request.getPatientIdNumber()).orElse(new Patient());
        patient.setIdNumber(request.getPatientIdNumber().trim());
        patient.setName(request.getPatientName().trim());
        patient.setSurname(request.getPatientSurname().trim());
        Patient savedPatient = patientRepository.save(patient);

        Report report = mapper.createReportFromReportSaveRequest(request);
        report.setUser(user);
        report.setPatient(savedPatient);
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

    public void saveDummyReports(ReportSaveRequest request, User user) {
        Patient patient = Patient.builder()
                .idNumber(request.getPatientIdNumber())
                .name(request.getPatientName())
                .surname(request.getPatientSurname()).build();
        Patient savedPatient = patientRepository.save(patient);
        Report report = mapper.createReportFromReportSaveRequest(request);
        report.setUser(user);
        report.setPatient(savedPatient);
        report.setImageOfReport(request.getImageOfReport());
        reportRepository.save(report);
    }

    public Page<ReportResponse> getAllReports(Pageable pageable, String myReports) {

        Specification<Report> spec = Specification.where(null);

        if (!pageable.getSort().toString().contains("laborant")) {
            if (myReports != null && !myReports.isEmpty()) {
                spec = spec.and((root, query, builder) ->
                        builder.equal(root.get("user").get("username"), myReports)
                );
                return reportRepository.findAll(spec, pageable).map(mapper::createReportResponseFromReport);
            }
            return reportRepository.findAll(pageable).map(mapper::createReportResponseFromReport);
        }

        if (pageable.getSort().toString().contains("ASC"))
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by("user.fullName", "id").ascending());//id: r.id (report.id)
        else
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by("user.fullName", "id").descending());

//        When you don't give the second parameter (id), the records on the first 2 pages are the same.

        if (myReports != null && !myReports.isEmpty()) {
            return reportRepository.getReportsSortedByLaborantForMyReports(myReports, pageable).map(mapper::createReportResponseFromReport);
        }

        return reportRepository.getReportsSortedByLaborant(pageable).map(mapper::createReportResponseFromReport);
    }

    public Page<ReportResponse> searchInReports(Pageable pageable, String searchTerm, String startDate, String endDate, String myReports) {

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

        if (myReports != null && !myReports.isEmpty()) {
            spec = spec.and((root, query, builder) ->
                    builder.equal(root.get("user").get("username"), myReports)
            );
        }

        if (searchTerm != null && !searchTerm.isEmpty()) {
            spec = spec.and((root, query, builder) ->
                    builder.or(
                            builder.like(builder.lower(root.get("fileNumber")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("patient").get("idNumber")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("patient").get("name")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("patient").get("surname")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("diagnosisTitle")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("diagnosisDetails")), "%" + searchTerm.toLowerCase() + "%"),
                            builder.like(builder.lower(root.get("user").get("fullName")), "%" + searchTerm.toLowerCase() + "%")
                    )
            );
        }
        return reportRepository.findAll(spec, pageable).map(mapper::createReportResponseFromReport);
    }

    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(Messages.REPORT_NOT_FOUND_WITH_ID, id)));

        if (report.getImageOfReport() != null && !report.getImageOfReport().equals("sampleReport.png"))
            fileService.deleteReportImage(report.getImageOfReport());

        reportRepository.delete(report);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.warn("The report with id " + id + " has been deleted by laborant " + username);
    }

    public ReportResponse getReportById(Long id) {
        Report report = reportRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(Messages.REPORT_NOT_FOUND_WITH_ID, id)));
        return mapper.createReportResponseFromReport(report);
    }

    public ResponseEntity<?> updateReport(Long id, boolean removeImage, ReportUpdateRequest request) {

        Report report = reportRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(Messages.REPORT_NOT_FOUND_WITH_ID, id)));

        if (request.getImageOfReport() != null) {
            try {
                if (report.getImageOfReport() != null && !report.getImageOfReport().equals("sampleReport.png"))
                    fileService.deleteReportImage(report.getImageOfReport());
                String imageName = fileService.writeBase64EncodedStringToFileForReportPicture(request.getImageOfReport());
                report.setImageOfReport(imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (removeImage) {
            if (report.getImageOfReport() != null && !report.getImageOfReport().equals("sampleReport.png"))
                fileService.deleteReportImage(report.getImageOfReport());
            report.setImageOfReport(null);
        }

        Patient patient = patientRepository.findByIdNumber(request.getPatientIdNumber()).orElse(new Patient());
        patient.setIdNumber(request.getPatientIdNumber().trim());
        patient.setName(request.getPatientName().trim());
        patient.setSurname(request.getPatientSurname().trim());
        Patient savedPatient = patientRepository.save(patient);

        report.setFileNumber(request.getFileNumberWithId().split("-")[0]);
        report.setPatient(savedPatient);
        report.setDiagnosisTitle(request.getDiagnosisTitle().trim());
        report.setDiagnosisDetails(request.getDiagnosisDetails().trim());
        report.setDateOfReport(request.getDateOfReport());

        Report savedReport = reportRepository.save(report);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.warn("The report with id " + id + " has been updated by laborant " + username);

        return ResponseEntity.ok().body(mapper.createReportResponseFromReport(savedReport));
    }
}
