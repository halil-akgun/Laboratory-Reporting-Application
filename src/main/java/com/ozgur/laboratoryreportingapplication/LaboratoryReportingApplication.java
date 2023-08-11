package com.ozgur.laboratoryreportingapplication;

import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.service.ReportService;
import com.ozgur.laboratoryreportingapplication.service.UserService;
import com.ozgur.laboratoryreportingapplication.shared.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.shared.ReportSaveUpdateRequest;
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

            ReportSaveUpdateRequest report = new ReportSaveUpdateRequest();
            RegisterRequest user = new RegisterRequest();
            Random random = new Random();

            for (int i = 1; i <= 33; i++) {
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
                User savedUser = userService.saveUser(user);


                for (int j = 1; j <= 3; j++) {
                    report.setDateOfReport(LocalDate.of(2022, random.nextInt(12) + 1, random.nextInt(28) + 1));
                    String monthValue = String.format("%02d", report.getDateOfReport().getMonthValue());
                    if (i < 10) {
                        report.setPatientName(((char) (random.nextInt(26) + 'A')) + ".Name0" + i);
                        report.setPatientSurname("Surname0" + i);
                        report.setFileNumber("2022" + monthValue + "0" + i + j);
                        report.setPatientIdNumber(String.valueOf(i).repeat(11));
                        report.setDiagnosisTitle("DiagnosisTitle0" + i);
                        report.setDiagnosisDetails("DiagnosisDetails0" + i);
                    } else {
                        report.setPatientName(((char) (random.nextInt(26) + 'A')) + ".Name" + i);
                        report.setPatientSurname("Surname" + i);
                        report.setFileNumber("2022" + monthValue + i + j);
                        report.setPatientIdNumber(String.valueOf(i).repeat(5) + i / 10);
                        report.setDiagnosisTitle("DiagnosisTitle" + i);
                        report.setDiagnosisDetails("DiagnosisDetails" + i);
                    }
                    reportService.saveDummyReports(report, savedUser);
                }

            }
        };
    }
}
