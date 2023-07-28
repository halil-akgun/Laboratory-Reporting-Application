package com.ozgur.laboratoryreportingapplication.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", HttpStatus.FORBIDDEN.value());
        errorBody.put("error", "Access Denied");
        errorBody.put("message", "You are not authorized to access this resource.");
        errorBody.put("path", request.getServletPath());

        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        mapper.writeValue(response.getOutputStream(), errorBody);
    }
}
