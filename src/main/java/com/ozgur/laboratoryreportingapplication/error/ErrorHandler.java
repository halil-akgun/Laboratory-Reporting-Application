package com.ozgur.laboratoryreportingapplication.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ErrorAttributes errorAttributes;

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<ApiError> handleAuthenticationException(Exception ex) {

        ApiError er = new ApiError(401,
                "Authentication failed", "/auth");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er);

    }

}
