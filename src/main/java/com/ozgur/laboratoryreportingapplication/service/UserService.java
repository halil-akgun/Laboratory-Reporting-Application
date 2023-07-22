package com.ozgur.laboratoryreportingapplication.service;

import com.ozgur.laboratoryreportingapplication.shared.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.shared.UserResponse;
import com.ozgur.laboratoryreportingapplication.shared.ResponseMessage;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.error.ConflictException;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import com.ozgur.laboratoryreportingapplication.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;


    public void saveAdmin(User user) {

        user.setUserRole(userRoleService.getUserRole(RoleType.ROLE_ADMIN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public boolean isExistsAdmin() {
        return userRepository.existsByUserRole(userRoleService.getUserRole(RoleType.ROLE_ADMIN));
    }

    public ResponseMessage<UserResponse> saveUser(RegisterRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(assistant -> {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME, request.getUsername()));
        });
        userRepository.findByHospitalIdNumber(request.getHospitalIdNumber()).ifPresent(assistant -> {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_HOSPITAL_ID_NUMBER, request.getHospitalIdNumber()));
        });

        User user = mapper.createUserFromRegisterRequest(request);

        user.setUserRole(userRoleService.getUserRole(RoleType.ROLE_LABORATORY_ASSISTANT));
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return ResponseMessage.<UserResponse>builder()
                .message("Assistant saved.")
                .httpStatus(HttpStatus.CREATED)
                .object(mapper.createUserResponseFromAssistant(savedUser)).build();
    }

    public Page<UserResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(mapper::createUserResponseFromAssistant);
    }
}
