package com.ozgur.laboratoryreportingapplication.service;

import com.ozgur.laboratoryreportingapplication.configuration.UserDetailsImpl;
import com.ozgur.laboratoryreportingapplication.entity.Report;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportSecurityServiceTest {

    @InjectMocks
    private ReportSecurityService reportSecurityService;

    @Mock
    private ReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void isAllowedToUpdateTest() {
        Long reportId = 1L;
        UserDetailsImpl loggedInUser = new UserDetailsImpl(new User());
        loggedInUser.getUser().setId(1L); // Logged-in user with ID 1

        Report report = new Report();
        report.setId(reportId);
        report.setUser(new User());
        report.getUser().setId(1L); // Report belongs to user with ID 1

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        // Test the case where the logged-in user has access
        boolean allowedToUpdate = reportSecurityService.isAllowedToUpdate(reportId, loggedInUser);
        assertTrue(allowedToUpdate);

        // Test the case where the logged-in user is not the owner but has admin rights
        loggedInUser.getUser().setUsername("admin");
        loggedInUser.getUser().setId(2L); // Different user ID
        allowedToUpdate = reportSecurityService.isAllowedToUpdate(reportId, loggedInUser);
        assertTrue(allowedToUpdate);

        // Test the case where the logged-in user is not the owner and not an admin
        loggedInUser.getUser().setUsername("user");
        allowedToUpdate = reportSecurityService.isAllowedToUpdate(reportId, loggedInUser);
        assertFalse(allowedToUpdate);
    }
}
