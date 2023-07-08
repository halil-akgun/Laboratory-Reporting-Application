package com.ozgur.laboratoryreportingapplication.service;

import com.ozgur.laboratoryreportingapplication.dto.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.dto.RegisterResponse;
import com.ozgur.laboratoryreportingapplication.dto.ResponseMessage;
import com.ozgur.laboratoryreportingapplication.entity.concretes.Assistant;
import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.exception.ConflictException;
import com.ozgur.laboratoryreportingapplication.repository.AssistantRepository;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import com.ozgur.laboratoryreportingapplication.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AssistantService {

    private final AssistantRepository assistantRepository;
    private final Mapper mapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;


    public ResponseMessage<RegisterResponse> save(RegisterRequest request) {
        assistantRepository.findByUsername(request.getUsername()).ifPresent(assistant -> {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME, request.getUsername()));
        });
        assistantRepository.findByHospitalIdNumber(request.getHospitalIdNumber()).ifPresent(assistant -> {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_HOSPITAL_ID_NUMBER, request.getHospitalIdNumber()));
        });

        Assistant assistant = mapper.createAssistantFromRegisterRequest(request);

        assistant.setUserRole(userRoleService.getUserRole(RoleType.LABORATORY_ASSISTANT));
        assistant.setPassword(passwordEncoder.encode(request.getPassword()));

        Assistant savedAssistant = assistantRepository.save(assistant);

        return ResponseMessage.<RegisterResponse>builder()
                .message("Assistant saved.")
                .httpStatus(HttpStatus.CREATED)
                .object(mapper.createRegisterResponseFromAssistant(savedAssistant)).build();
    }

}
