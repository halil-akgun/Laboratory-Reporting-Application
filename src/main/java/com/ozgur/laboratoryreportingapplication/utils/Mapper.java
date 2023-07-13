package com.ozgur.laboratoryreportingapplication.utils;

import com.ozgur.laboratoryreportingapplication.dto.RegisterResponse;
import com.ozgur.laboratoryreportingapplication.entity.User;
import org.springframework.stereotype.Component;

import com.ozgur.laboratoryreportingapplication.dto.RegisterRequest;

@Component
public class Mapper {
	
	public User createAssistantFromRegisterRequest(RegisterRequest request) {
		return User.builder()
				.username(request.getUsername())
				.name(request.getName())
				.surname(request.getSurname())
				.hospitalIdNumber(request.getHospitalIdNumber())
				.build();
	}

	public RegisterResponse createRegisterResponseFromAssistant(User assistant) {
		return RegisterResponse.builder()
				.username(assistant.getUsername())
				.name(assistant.getName())
				.surname(assistant.getSurname())
				.hospitalIdNumber(assistant.getHospitalIdNumber())
				.build();
	}
}
