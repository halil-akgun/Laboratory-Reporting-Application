package com.ozgur.laboratoryreportingapplication.controller;

import org.springframework.web.bind.annotation.*;

import com.ozgur.laboratoryreportingapplication.service.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {
	
	private AdminService adminService;
	
//	@Autowired // unnecessary as there is only one constructor
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}


}
