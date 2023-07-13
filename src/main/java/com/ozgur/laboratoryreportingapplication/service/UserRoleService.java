package com.ozgur.laboratoryreportingapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ozgur.laboratoryreportingapplication.entity.UserRole;
import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.exception.ConflictException;
import com.ozgur.laboratoryreportingapplication.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRoleService {

	private final UserRoleRepository userRoleRepository;

	public UserRole getUserRole(RoleType roleType) {

		return userRoleRepository.findByRoleType(roleType).orElse(null);

	}

	public List<UserRole> getAllUserRole() {
		return userRoleRepository.findAll();
	}
	
	  public UserRole save(RoleType roleType) {

	        if (userRoleRepository.existsByRoleType(roleType))
	            throw new ConflictException("This role is already registered.");

	        UserRole userRole = UserRole.builder().roleType(roleType).build();
	        return userRoleRepository.save(userRole);
	    }

}
