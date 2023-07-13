package com.ozgur.laboratoryreportingapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozgur.laboratoryreportingapplication.entity.UserRole;
import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{

	Optional<UserRole> findByRoleType(RoleType roleType);

	boolean existsByRoleType(RoleType roleType);

}
