package com.ozgur.laboratoryreportingapplication.utils;

import com.ozgur.laboratoryreportingapplication.entity.Report;
import com.ozgur.laboratoryreportingapplication.shared.*;
import com.ozgur.laboratoryreportingapplication.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Mapper {

    public static LoginResponse loginResponseFromUser(User user) {
        return LoginResponse.builder()
                .hospitalIdNumber(user.getHospitalIdNumber())
                .name(user.getName())
                .surname(user.getSurname())
                .image(user.getImage())
                .username(user.getUsername())
                .fullName(user.getFullName()).build();
    }

    public User createUserFromRegisterRequest(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername().trim())
                .name(request.getName().trim())
                .surname(request.getSurname().trim())
                .hospitalIdNumber(request.getHospitalIdNumber().trim())
                .build();
    }

    public UserResponse createUserResponseFromUser(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .image(user.getImage())
                .hospitalIdNumber(user.getHospitalIdNumber())
                .fullName(user.getFullName())
                .build();
    }

    public Report createReportFromReportSaveRequest(ReportSaveRequest request) {
        return Report.builder()
                .diagnosisTitle(request.getDiagnosisTitle().trim())
                .diagnosisDetails(request.getDiagnosisDetails().trim())
                .dateOfReport(request.getDateOfReport())
                .fileNumber(request.getFileNumber().trim())
                .timestamp(LocalDate.now()).build();
    }

    public ReportResponse createReportResponseFromReport(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .patientName(report.getPatient().getName())
                .patientSurname(report.getPatient().getSurname())
                .patientIdNumber(report.getPatient().getIdNumber())
                .patientId(report.getPatient().getId())
                .diagnosisTitle(report.getDiagnosisTitle())
                .diagnosisDetails(report.getDiagnosisDetails())
                .dateOfReport(report.getDateOfReport())
                .fileNumber(report.getFileNumber())
                .imageOfReport(report.getImageOfReport())
                .timestamp(report.getTimestamp())
                .laborantNameSurname(report.getUser().getFullName())
                .laborantUsername(report.getUser().getUsername())
                .build();
    }
}
