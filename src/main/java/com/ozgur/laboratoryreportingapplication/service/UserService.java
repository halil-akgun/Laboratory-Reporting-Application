package com.ozgur.laboratoryreportingapplication.service;

import com.ozgur.laboratoryreportingapplication.error.ResourceNotFoundException;
import com.ozgur.laboratoryreportingapplication.configuration.UserDetailsImpl;
import com.ozgur.laboratoryreportingapplication.shared.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.shared.UserUpdateRequest;
import com.ozgur.laboratoryreportingapplication.shared.UserResponse;
import com.ozgur.laboratoryreportingapplication.shared.ResponseMessage;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.error.ConflictException;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import com.ozgur.laboratoryreportingapplication.utils.FileService;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import com.ozgur.laboratoryreportingapplication.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;


    public void saveAdmin(User user) {

        user.setUserRole(userRoleService.getUserRole(RoleType.ROLE_ADMIN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public boolean isExistsAdmin() {
        return userRepository.existsByUserRole(userRoleService.getUserRole(RoleType.ROLE_ADMIN));
    }

    public User saveUser(RegisterRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(assistant -> {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_WITH_USERNAME, request.getUsername()));
        });
        userRepository.findByHospitalIdNumber(request.getHospitalIdNumber()).ifPresent(assistant -> {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_WITH_HOSPITAL_ID_NUMBER, request.getHospitalIdNumber()));
        });

        User user = mapper.createUserFromRegisterRequest(request);

        user.setFullName(user.getName() + " " + user.getSurname());
        user.setUserRole(userRoleService.getUserRole(RoleType.ROLE_LABORATORY_ASSISTANT));
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public Page<UserResponse> getAllUsers(Pageable pageable, UserDetailsImpl userDetails) {
        return userRepository.findByUsernameNot(pageable, userDetails.getUsername())
                .map(mapper::createUserResponseFromAssistant);
    }

    public UserResponse getUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException(String.format(Messages.USER_NOT_FOUND_WITH_USERNAME, username)));
        return mapper.createUserResponseFromAssistant(user);
    }

    public ResponseMessage<UserResponse> updateUser(String username, UserUpdateRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException(String.format(Messages.USER_NOT_FOUND_WITH_USERNAME, username)));

        if (!username.equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername()))
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_WITH_USERNAME, request.getUsername()));
        if (!user.getHospitalIdNumber().equals(request.getHospitalIdNumber())
                && userRepository.existsByHospitalIdNumber(request.getHospitalIdNumber()))
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_WITH_HOSPITAL_ID_NUMBER, request.getHospitalIdNumber()));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getSurname() != null) user.setSurname(request.getSurname());
        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getHospitalIdNumber() != null) user.setHospitalIdNumber(request.getHospitalIdNumber());
        if (request.getImage() != null) {
            String oldImageName = user.getImage();
            try {
                String imageName = fileService.writeBase64EncodedStringToFileForProfilePicture(request.getImage());
                user.setImage(imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileService.deleteFile(oldImageName);
        }
        user.setFullName(user.getName() + " " + user.getSurname());

        User savedUser = userRepository.save(user);

        return ResponseMessage.<UserResponse>builder()
                .message("User updated.")
                .httpStatus(HttpStatus.OK)
                .object(mapper.createUserResponseFromAssistant(savedUser)).build();
    }

    public User getUserPojoWithUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException(String.format(Messages.USER_NOT_FOUND_WITH_USERNAME, username)));
    }
}
