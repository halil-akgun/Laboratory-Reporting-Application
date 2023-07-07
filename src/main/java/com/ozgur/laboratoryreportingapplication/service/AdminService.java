package com.ozgur.laboratoryreportingapplication.service;

import java.util.Objects;

//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ozgur.laboratoryreportingapplication.dto.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.entity.concretes.Admin;
import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.exception.ConflictException;
import com.ozgur.laboratoryreportingapplication.repository.AdminRepository;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import com.ozgur.laboratoryreportingapplication.utils.Messages;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final Mapper mapper;
    private final UserRoleService userRoleService;
//    private final PasswordEncoder passwordEncoder;


    public void save(RegisterRequest request) {
        adminRepository.findByUsername(request.getUsername()).ifPresent(admin -> {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME, request.getUsername()));
        });
        adminRepository.findByHospitalIdNumber(request.getHospitalIdNumber()).ifPresent(admin -> {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_HOSPITAL_ID_NUMBER, request.getHospitalIdNumber()));
        });

        Admin admin = mapper.createAdminFromRegisterRequest(request);
        admin.setBuilt_in(false);

        if (Objects.equals(request.getUsername(), "admin")) admin.setBuilt_in(true);

        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
//        admin.setPassword(passwordEncoder.encode(request.getPassword())); TODO
        admin.setPassword(request.getPassword());

        adminRepository.save(admin);
    }

    public long countAllAdmin() {
        return adminRepository.count();
    }
}
