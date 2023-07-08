package com.ozgur.laboratoryreportingapplication.controller;

import com.ozgur.laboratoryreportingapplication.dto.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.dto.RegisterResponse;
import com.ozgur.laboratoryreportingapplication.dto.ResponseMessage;
import com.ozgur.laboratoryreportingapplication.service.AssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/assistants")
@RequiredArgsConstructor
public class AssistantController {

    private final AssistantService assistantService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage<RegisterResponse> save(@Valid @RequestBody RegisterRequest request) {
        return assistantService.save(request);
    }

}
