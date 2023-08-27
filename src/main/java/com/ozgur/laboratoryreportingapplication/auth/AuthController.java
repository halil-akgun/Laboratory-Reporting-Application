package com.ozgur.laboratoryreportingapplication.auth;

import com.ozgur.laboratoryreportingapplication.shared.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/auth")
    AuthResponse handleAuthentication(@RequestBody Credentials credentials) {

        return authService.authenticate(credentials);
    }

    @PostMapping("/logout2")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_LABORANT')")
    ResponseMessage<?> handleLogout(@RequestHeader(name = "Authorization") String authorization) {

        String token = authorization.substring(7);
        authService.clearToken(token);

        return new ResponseMessage<>().toBuilder().message("Logout success").build();
    }
}
