package com.ozgur.laboratoryreportingapplication.utils;

import com.ozgur.laboratoryreportingapplication.dto.RegisterResponse;
import com.ozgur.laboratoryreportingapplication.entity.concretes.Assistant;
import org.springframework.stereotype.Component;

import com.ozgur.laboratoryreportingapplication.dto.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.entity.concretes.Admin;

@Component
public class Mapper {
	
	public Assistant createAssistantFromRegisterRequest(RegisterRequest request) {
		return Assistant.builder()
				.username(request.getUsername())
				.name(request.getName())
				.surname(request.getSurname())
				.hospitalIdNumber(request.getHospitalIdNumber())
				.build();
	}

	public RegisterResponse createRegisterResponseFromAssistant(Assistant assistant) {
		return RegisterResponse.builder()
				.username(assistant.getUsername())
				.name(assistant.getName())
				.surname(assistant.getSurname())
				.hospitalIdNumber(assistant.getHospitalIdNumber())
				.build();
	}
}
