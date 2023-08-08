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
                .username(user.getUsername()).build();
    }

    public User createUserFromRegisterRequest(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .surname(request.getSurname())
                .hospitalIdNumber(request.getHospitalIdNumber())
                .build();
    }

    public UserResponse createUserResponseFromAssistant(User assistant) {
        return UserResponse.builder()
                .username(assistant.getUsername())
                .name(assistant.getName())
                .surname(assistant.getSurname())
                .image(assistant.getImage())
                .hospitalIdNumber(assistant.getHospitalIdNumber())
                .build();
    }

    public Report createReportFromReportSaveUpdateRequest(ReportSaveUpdateRequest request) {
        return Report.builder()
                .patientName(request.getPatientName())
                .patientSurname(request.getPatientSurname())
                .diagnosisTitle(request.getDiagnosisTitle())
                .diagnosisDetails(request.getDiagnosisDetails())
                .dateOfReport(request.getDateOfReport())
                .fileNumber(request.getFileNumber())
                .patientIdNumber(request.getPatientIdNumber())
                .timestamp(LocalDate.now()).build();
    }

    public ReportResponse createReportResponseFromReport(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .patientName(report.getPatientName())
                .patientSurname(report.getPatientSurname())
                .diagnosisTitle(report.getDiagnosisTitle())
                .diagnosisDetails(report.getDiagnosisDetails())
                .dateOfReport(report.getDateOfReport())
                .fileNumber(report.getFileNumber())
                .patientIdNumber(report.getPatientIdNumber())
                .imageOfReport(report.getImageOfReport())
                .timestamp(report.getTimestamp())
                .laborantNameSurname(report.getUser().getName() + " " + report.getUser().getSurname())
                .build();
    }
}
