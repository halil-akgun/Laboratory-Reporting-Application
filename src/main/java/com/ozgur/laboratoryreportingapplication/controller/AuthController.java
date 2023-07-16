package com.ozgur.laboratoryreportingapplication.controller;

import com.ozgur.laboratoryreportingapplication.security.UserDetailsImpl;
import com.ozgur.laboratoryreportingapplication.shared.annotation.CurrentUser;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/auth")
    ResponseEntity<?> authenticateUser(@CurrentUser UserDetailsImpl userDetails) {

        logger.info("User logged in with username " + userDetails.getUsername());

        return ResponseEntity.ok(Mapper.loginResponseFromUser(userDetails.getUser()));
    }

}
