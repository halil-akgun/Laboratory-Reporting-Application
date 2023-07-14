package com.ozgur.laboratoryreportingapplication.controller;

import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.error.ApiError;
import com.ozgur.laboratoryreportingapplication.error.ResourceNotFoundException;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class AuthController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth")
    ResponseEntity<?> authenticateUser(@RequestHeader(name = "Authorization") String authorization) {
        String base64Encoded = authorization.split("Basic ")[1];
        String decoded = new String(Base64.getDecoder().decode(base64Encoded));
        String[] parts = decoded.split(":");
        String username = parts[0];
        System.out.println(1);
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            ApiError error = new ApiError(401, "Unauthorized request", "/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        System.out.println(2);
        return ResponseEntity.ok(Mapper.loginResponseFromUser(user));
    }

}
