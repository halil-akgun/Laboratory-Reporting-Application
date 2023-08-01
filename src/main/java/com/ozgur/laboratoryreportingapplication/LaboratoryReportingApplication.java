package com.ozgur.laboratoryreportingapplication;

import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.service.UserService;
import com.ozgur.laboratoryreportingapplication.shared.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.service.UserRoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.io.File;

@SpringBootApplication
@RequiredArgsConstructor
public class LaboratoryReportingApplication implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(LaboratoryReportingApplication.class, args);
    }

//  CommandLineRunner is implemented and the run method is overridden.
//	In this way, when the application starts working, the following methods will be run first.
    @Override
    public void run(String... args) {

        // Fill the RoleType table
        if (userRoleService.getAllUserRole().size() == 0) {
            userRoleService.save(RoleType.ROLE_ADMIN);
            userRoleService.save(RoleType.ROLE_LABORATORY_ASSISTANT);
        }

        // Create ADMIN (built_in)
        if (!userService.isExistsAdmin()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("12345678");
            admin.setName("Admin");
            admin.setSurname("Admin");
            admin.setHospitalIdNumber("0000000");
            userService.saveAdmin(admin);
        }
    }

    @Bean
    @Profile("dev")
    CommandLineRunner createDummyUsers() {
        return (args) -> {
            for (int i = 1; i < 33; i++) {
                RegisterRequest user = new RegisterRequest();
                user.setUsername("user" + i);
                user.setPassword("12345678");
                user.setName("Name" + i);
                user.setSurname("Surname" + i);
                if (i < 10) user.setHospitalIdNumber("000000" + i);
                else user.setHospitalIdNumber("00000" + i);
                userService.saveUser(user);
            }
        };
    }
}
