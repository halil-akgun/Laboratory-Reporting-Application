package com.ozgur.laboratoryreportingapplication.utils;

import com.ozgur.laboratoryreportingapplication.shared.LoginResponse;
import com.ozgur.laboratoryreportingapplication.shared.UserResponse;
import com.ozgur.laboratoryreportingapplication.entity.User;
import org.springframework.stereotype.Component;

import com.ozgur.laboratoryreportingapplication.shared.RegisterRequest;

@Component
public class Mapper {

    public static LoginResponse loginResponseFromUser(User user) {
        return LoginResponse.builder()
                .hospitalIdNumber(user.getHospitalIdNumber())
                .name(user.getName())
                .surname(user.getSurname())
                .image(user.getImage())
                .username(user.getUsername()).build();
    }

    public User createUserFromRegisterRequest(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .surname(request.getSurname())
                .hospitalIdNumber(request.getHospitalIdNumber())
                .build();
    }

    public UserResponse createUserResponseFromAssistant(User assistant) {
        return UserResponse.builder()
                .username(assistant.getUsername())
                .name(assistant.getName())
                .surname(assistant.getSurname())
                .image(assistant.getImage())
                .hospitalIdNumber(assistant.getHospitalIdNumber())
                .build();
    }
}
