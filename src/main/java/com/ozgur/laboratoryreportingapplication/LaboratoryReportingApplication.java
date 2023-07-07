package com.ozgur.laboratoryreportingapplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ozgur.laboratoryreportingapplication.dto.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.service.AdminService;
import com.ozgur.laboratoryreportingapplication.service.UserRoleService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class LaboratoryReportingApplication implements CommandLineRunner {

	private final UserRoleService userRoleService;
	private final AdminService adminService;

	public static void main(String[] args) {
		SpringApplication.run(LaboratoryReportingApplication.class, args);
	}


//	CommandLineRunner is implemented and the run method is overridden.
//	In this way, when the application starts working, the following methods will be run first.
	@Override
	public void run(String... args) throws Exception {

		// Fill the RoleType table
		if (userRoleService.getAllUserRole().size() == 0) {
			userRoleService.save(RoleType.ADMIN);
			userRoleService.save(RoleType.LABORATORY_ASSISTANT);
		}

		// Create ADMIN (built_in)
		if (adminService.countAllAdmin() == 0) {
			RegisterRequest admin = new RegisterRequest();
			admin.setUsername("admin");
			admin.setPassword("12345678");
			admin.setName("Admin");
			admin.setSurname("Admin");
			admin.setHospitalIdNumber("0000000");
			adminService.save(admin);
		}
	}
}
