package com.ozgur.laboratoryreportingapplication.controller;

import com.ozgur.laboratoryreportingapplication.dto.LoginRequest;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.exception.ResourceNotFoundException;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth")
    ResponseEntity authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("User not found.")
        );

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }

        Map<String, String> response = new HashMap<>();
        response.put("username", user.getUsername());
        return ResponseEntity.ok(response);
    }

}
