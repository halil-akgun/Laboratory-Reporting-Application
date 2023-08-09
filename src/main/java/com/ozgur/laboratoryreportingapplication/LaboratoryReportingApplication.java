package com.ozgur.laboratoryreportingapplication;

import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.error.ResourceNotFoundException;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import com.ozgur.laboratoryreportingapplication.service.ReportService;
import com.ozgur.laboratoryreportingapplication.service.UserService;
import com.ozgur.laboratoryreportingapplication.shared.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.shared.ReportSaveUpdateRequest;
import com.ozgur.laboratoryreportingapplication.utils.Messages;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.service.UserRoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Random;

@SpringBootApplication
@RequiredArgsConstructor
public class LaboratoryReportingApplication implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ReportService reportService;

    public static void main(String[] args) {
        SpringApplication.run(LaboratoryReportingApplication.class, args);
    }

    //  CommandLineRunner is implemented and the run method is overridden.
//	In this way, when the application starts working, the following methods will be run first.
    @Override
    public void run(String... args) {

        // Fill the RoleType table
        if (userRoleService.getAllUserRole().isEmpty()) {
            userRoleService.save(RoleType.ROLE_ADMIN);
            userRoleService.save(RoleType.ROLE_LABORATORY_ASSISTANT);
        }

        // Create ADMIN (built_in)
        if (!userService.isExistsAdmin()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("12345678");
            admin.setName("Halil");
            admin.setSurname("Akgun");
            admin.setHospitalIdNumber("0000000");
            userService.saveAdmin(admin);
        }
    }

    @Bean
    @Profile("dev")
    CommandLineRunner createDummyUsers() {
        return (args) -> {
            for (int i = 1; i <= 33; i++) {
                RegisterRequest user = new RegisterRequest();
                user.setPassword("12345678");
                if (i < 10) {
                    user.setUsername("user0" + i);
                    user.setName("Name0" + i);
                    user.setSurname("Surname0" + i);
                    user.setHospitalIdNumber("000000" + i);
                } else {
                    user.setUsername("user" + i);
                    user.setName("Name" + i);
                    user.setSurname("Surname" + i);
                    user.setHospitalIdNumber("00000" + i);
                }
                userService.saveUser(user);
            }

            User admin = userRepository.findByUsername("admin").orElseThrow(() ->
                    new ResourceNotFoundException(String.format(Messages.USER_NOT_FOUND_WITH_USERNAME, "admin")));
            User user = userRepository.findByUserRoleNot(userRoleService.getUserRole(RoleType.ROLE_ADMIN)).get(0);
            ReportSaveUpdateRequest report = new ReportSaveUpdateRequest();
            Random random = new Random();
            for (int i = 1; i <= 30; i++) {
                report.setDateOfReport(LocalDate.of(2023, 1, i));
                if (i < 10) {
                    report.setPatientName(((char) (random.nextInt(26) + 'A')) + ".Name0" + i);
                    report.setPatientSurname("Surname0" + i);
                    report.setFileNumber("00000000" + i);
                    report.setPatientIdNumber(String.valueOf(i).repeat(11));
                    report.setDiagnosisTitle("DiagnosisTitle0" + i);
                    report.setDiagnosisDetails("DiagnosisDetails0" + i);
                    reportService.saveDummyReports(report, admin);
                } else {
                    report.setPatientName(((char) (random.nextInt(26) + 'A')) + ".Name" + i);
                    report.setPatientSurname("Surname" + i);
                    report.setFileNumber("0000000" + i);
                    report.setPatientIdNumber(String.valueOf(i).repeat(5) + i / 10);
                    report.setDiagnosisTitle("DiagnosisTitle" + i);
                    report.setDiagnosisDetails("DiagnosisDetails" + i);
                    reportService.saveDummyReports(report, user);
                }
            }
        };
    }
}
