package com.ozgur.laboratoryreportingapplication.service;

import com.ozgur.laboratoryreportingapplication.entity.UserRole;
import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.error.ConflictException;
import com.ozgur.laboratoryreportingapplication.repository.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRoleServiceTest {

    private UserRoleService userRoleService;

    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setUp() {
        userRoleRepository = mock(UserRoleRepository.class);
        userRoleService = new UserRoleService(userRoleRepository);
    }

    @Test
    void getUserRoleTest() {

        RoleType roleType = RoleType.ROLE_ADMIN;
        UserRole expectedUserRole = UserRole.builder().roleType(roleType).build();

        when(userRoleRepository.findByRoleType(roleType)).thenReturn(Optional.of(expectedUserRole));

        UserRole resultUserRole = userRoleService.getUserRole(roleType);

        assertNotNull(resultUserRole);
        assertEquals(expectedUserRole, resultUserRole);
    }


    @Test
    void getAllUserRoleTest() {

        UserRole userRole1 = UserRole.builder().roleType(RoleType.ROLE_ADMIN).build();
        UserRole userRole2 = UserRole.builder().roleType(RoleType.ROLE_LABORANT).build();
        when(userRoleRepository.findAll()).thenReturn(List.of(userRole1, userRole2));

        var allUserRoles = userRoleService.getAllUserRole();

        assertEquals(2, allUserRoles.size());
        assertTrue(allUserRoles.contains(userRole1));
        assertTrue(allUserRoles.contains(userRole2));
    }

    @Test
    void save_UniqueRoleType_SavesAndReturnsUserRole() {

        RoleType roleType = RoleType.ROLE_LABORANT;
        when(userRoleRepository.existsByRoleType(roleType)).thenReturn(false);

        userRoleService.save(roleType);
        UserRole resultUserRole = UserRole.builder().roleType(roleType).build();
        when(userRoleService.save(roleType)).thenReturn(resultUserRole);

        assertNotNull(resultUserRole);
        assertEquals(roleType, resultUserRole.getRoleType());
        verify(userRoleRepository, times(1)).save(Mockito.any(UserRole.class));
    }

    @Test
    void save_DuplicateRoleType_ThrowsConflictException() {

        RoleType roleType = RoleType.ROLE_ADMIN;
        when(userRoleRepository.existsByRoleType(roleType)).thenReturn(true);

        assertThrows(ConflictException.class, () -> userRoleService.save(roleType));
        verify(userRoleRepository, never()).save(Mockito.any(UserRole.class));
    }
}
