package com.ozgur.laboratoryreportingapplication.service;

import com.ozgur.laboratoryreportingapplication.configuration.UserDetailsImpl;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.entity.UserRole;
import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.error.ConflictException;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import com.ozgur.laboratoryreportingapplication.shared.*;
import com.ozgur.laboratoryreportingapplication.utils.FileService;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private Mapper mapper;

    @Mock
    private FileService fileService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAdmin() {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword("adminPassword");
        adminUser.setUserRole(userRoleService.getUserRole(RoleType.ROLE_ADMIN));

        when(userRepository.save(any(User.class))).thenReturn(adminUser);

        userService.saveAdmin(adminUser);

        verify(userRepository, times(1)).save(adminUser);
    }

    @Test
    void isExistsAdmin() {
        UserRole adminRole = userRoleService.getUserRole(RoleType.ROLE_ADMIN);
        when(userRepository.existsByUserRole(adminRole)).thenReturn(true);

        boolean result = userService.isExistsAdmin();

        assertTrue(result);
    }

    @Test
    void saveUser() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setPassword("testpassword");
        registerRequest.setHospitalIdNumber("123456789");

        when(mapper.createUserFromRegisterRequest(any(RegisterRequest.class))).thenReturn(new User());

        User existingUser = new User();
        existingUser.setUsername("testuser");
        existingUser.setHospitalIdNumber("123456");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));
        when(userRepository.findByHospitalIdNumber("123456")).thenReturn(Optional.of(existingUser));

        assertThrows(ConflictException.class, () -> userService.saveUser(registerRequest));

        registerRequest.setUsername("newuser");
        registerRequest.setHospitalIdNumber("789012345");

        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setHospitalIdNumber("789012345");
        newUser.setPassword("encodedpassword");

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User savedUser = userService.saveUser(registerRequest);

        assertNotNull(savedUser);
        assertEquals("newuser", savedUser.getUsername());
        assertEquals("encodedpassword", savedUser.getPassword());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository.findByUsernameNot(eq(pageable), any())).thenReturn(Page.empty());

        Page<UserResponse> result = userService.getAllUsers(pageable, new UserDetailsImpl(new User()));

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getUser() {
        String username = "testuser";

        User testUser = new User();
        testUser.setUsername(username);
        testUser.setFullName("Test User");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(username);
        userResponse.setFullName("Test User");

        when(mapper.createUserResponseFromUser(testUser)).thenReturn(userResponse);
        when(userService.getUser(username)).thenReturn(userResponse);

        UserResponse result = userService.getUser(username);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("Test User", result.getFullName());
    }

    @Test
    void updateUser() {
        String username = "testuser";

        UserUpdateRequest request = new UserUpdateRequest();
        request.setUsername(username);
        request.setName("updated name");
        request.setSurname("updated surname");

        User testUser = new User();
        testUser.setUsername(request.getUsername());
        testUser.setName(request.getName());
        testUser.setSurname(request.getSurname());
        testUser.setHospitalIdNumber("999999999");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        UserResponse userResponse = UserResponse.builder()
                .username(testUser.getUsername())
                .name(testUser.getName())
                .surname(testUser.getSurname()).build();

        ResponseMessage<UserResponse> result = userService.updateUser(username, request);
        result.setObject(userResponse);

        when(userRepository.save(testUser)).thenReturn(new User());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(mapper.createUserResponseFromUser(testUser)).thenReturn(userResponse);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        assertEquals("testuser", result.getObject().getUsername());
        assertEquals("updated surname", result.getObject().getSurname());
    }

    @Test
    void getUserPojoWithUsername() {
        String username = "testuser";

        User testUser = new User();
        testUser.setUsername(username);
        testUser.setFullName("Test User");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        User result = userService.getUserPojoWithUsername(username);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("Test User", result.getFullName());
    }

    @Test
    void deleteUser() {
        String username = "testuser";
        String imageName = "test_image.jpg";

        User testUser = new User();
        testUser.setUsername(username);
        testUser.setFullName("Test User");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        fileService.deleteProfileImage(imageName);
        verify(fileService, times(1)).deleteProfileImage(eq(imageName));

        ResponseMessage<?> result = userService.deleteUser(username, false);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getHttpStatus());
        verify(userRepository, times(1)).delete(testUser);
    }
}
