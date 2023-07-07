package com.ozgur.laboratoryreportingapplication.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ozgur.laboratoryreportingapplication.dto.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.service.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {
	
	private AdminService adminService;
	
//	@Autowired // unnecessary as there is only one constructor
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@Valid @RequestBody RegisterRequest request) {
		adminService.save(request);
	}

}
