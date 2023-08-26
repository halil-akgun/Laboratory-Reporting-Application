package com.ozgur.laboratoryreportingapplication.service;

import com.ozgur.laboratoryreportingapplication.entity.Patient;
import com.ozgur.laboratoryreportingapplication.entity.Report;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.repository.PatientRepository;
import com.ozgur.laboratoryreportingapplication.repository.ReportRepository;
import com.ozgur.laboratoryreportingapplication.shared.*;
import com.ozgur.laboratoryreportingapplication.utils.FileService;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserService userService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private Mapper mapper;

    @Mock
    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveReportTest() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken("testUsername", "testPassword");
        securityContext.setAuthentication(authentication);

        when(mapper.createReportFromReportSaveRequest(any(ReportSaveRequest.class))).thenReturn(new Report());

        ReportSaveRequest request = new ReportSaveRequest();
        request.setPatientIdNumber("11111111111");
        request.setPatientName("John");
        request.setPatientSurname("Doe");
        request.setImageOfReport("base64encodedimage");

        when(userService.getUserPojoWithUsername("testUsername")).thenReturn(new User());
        when(patientRepository.findByIdNumber("11111111111")).thenReturn(Optional.of(new Patient()));
        when(patientRepository.findByIdNumber("11111111111")).thenReturn(java.util.Optional.empty());
        when(patientRepository.save(any(Patient.class))).thenReturn(new Patient());
        when(reportRepository.save(any(Report.class))).thenReturn(new Report());

        when(mapper.createReportResponseFromReport(any(Report.class))).thenReturn(new ReportResponse());

        ResponseMessage<ReportResponse> response = reportService.saveReport(request);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());
    }

    @Test
    void getAllReportsTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Report> reportPage = new PageImpl<>(new ArrayList<>()); // Size: 0
        when(reportRepository.findAll(pageable)).thenReturn(reportPage);
        when(mapper.createReportResponseFromReport(any(Report.class))).thenReturn(new ReportResponse());

        Page<ReportResponse> response = reportService.getAllReports(pageable, null);

        assertNotNull(response);
        assertEquals(0, response.getTotalElements());
    }

    @Test
    void searchInReportsTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Report> reportPage = new PageImpl<>(new ArrayList<>());
        when(reportRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(reportPage);
        when(mapper.createReportResponseFromReport(any())).thenReturn(new ReportResponse());

        Page<ReportResponse> response = reportService.searchInReports(pageable, null, null, null, null);

        assertNotNull(response);
        assertEquals(0, response.getTotalElements());
    }

    @Test
    void deleteReportTest() {
        Long reportId = 1L;
        Report report = new Report();
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        doNothing().when(fileService).deleteReportImage(anyString());
        doNothing().when(reportRepository).delete(report);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken("testUsername", "testPassword");
        securityContext.setAuthentication(authentication);

        assertDoesNotThrow(() -> reportService.deleteReport(reportId)); // does not throw exception
    }

    @Test
    void getReportByIdTest() {
        Long reportId = 1L;
        Report report = new Report();
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        when(mapper.createReportResponseFromReport(any())).thenReturn(new ReportResponse());

        ReportResponse response = reportService.getReportById(reportId);

        assertNotNull(response);
    }

    @Test
    void updateReportTest() {
        Long reportId = 1L;
        ReportUpdateRequest request = new ReportUpdateRequest();
        request.setPatientIdNumber("11111111111");
        request.setPatientName("John");
        request.setPatientSurname("Doe");
        request.setImageOfReport("base64encodedimage");
        request.setFileNumberWithId("999999999-3");
        request.setDiagnosisTitle("qwer");
        request.setDiagnosisDetails("zzzzzzzzzzzzzzzzzzzzzzz");

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(new Report()));
        when(patientRepository.findByIdNumber(request.getPatientIdNumber())).thenReturn(Optional.empty());
        when(patientRepository.save(any(Patient.class))).thenReturn(new Patient());
        when(reportRepository.save(any(Report.class))).thenReturn(new Report());
        when(mapper.createReportResponseFromReport(any(Report.class))).thenReturn(new ReportResponse());

        ResponseEntity<?> response = reportService.updateReport(reportId, false, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
