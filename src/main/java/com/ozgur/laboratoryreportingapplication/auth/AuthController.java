package com.ozgur.laboratoryreportingapplication.auth;

import com.ozgur.laboratoryreportingapplication.configuration.UserDetailsImpl;
import com.ozgur.laboratoryreportingapplication.controller.UserController;
import com.ozgur.laboratoryreportingapplication.shared.annotation.CurrentUser;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private AuthService authService;


    @PostMapping("/auth")
    AuthResponse authenticateUser(@RequestBody Credentials credentials) {

        return authService.authenticate(credentials);
    }

}
