package com.ozgur.laboratoryreportingapplication.controller;

import com.ozgur.laboratoryreportingapplication.dto.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.dto.RegisterResponse;
import com.ozgur.laboratoryreportingapplication.dto.ResponseMessage;
import com.ozgur.laboratoryreportingapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/assistants")
@RequiredArgsConstructor
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage<RegisterResponse> save(@Valid @RequestBody RegisterRequest request) {

        ResponseMessage<RegisterResponse> response = userService.saveAdmin(request);
        logger.info("Assistant saved with username " + request.getUsername());
        return response;
    }

}
