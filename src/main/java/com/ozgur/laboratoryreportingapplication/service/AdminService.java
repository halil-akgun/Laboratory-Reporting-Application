package com.ozgur.laboratoryreportingapplication.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ozgur.laboratoryreportingapplication.entity.concretes.Admin;
import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.repository.AdminRepository;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;


    public void save(Admin admin) {

        admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
//        admin.setPassword(request.getPassword());

        Admin savedAdmin = adminRepository.save(admin);
    }

    public long countAllAdmin() {
        return adminRepository.count();
    }
}
