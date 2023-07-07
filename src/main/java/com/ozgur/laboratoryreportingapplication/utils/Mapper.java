package com.ozgur.laboratoryreportingapplication.utils;

import org.springframework.stereotype.Component;

import com.ozgur.laboratoryreportingapplication.dto.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.entity.concretes.Admin;

@Component
public class Mapper {
	
	public Admin createAdminFromRegisterRequest(RegisterRequest request) {
		return Admin.builder()
				.username(request.getUsername())
				.name(request.getName())
				.surname(request.getSurname())
				.hospitalIdNumber(request.getHospitalIdNumber())
				.build();
	}

}
