package com.ozgur.laboratoryreportingapplication.auth;

import com.ozgur.laboratoryreportingapplication.entity.Token;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.repository.TokenRepository;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateTest() {
        Credentials credentials = new Credentials("testUsername", "testPassword");
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("testPassword", "encodedPassword")).thenReturn(true);

        AuthResponse authResponse = authService.authenticate(credentials);

        assertNotNull(authResponse);
        assertNotNull(authResponse.getUser());
        assertEquals("testUsername", authResponse.getUser().getUsername());
        assertNotNull(authResponse.getToken());
    }

    @Test
    void getUserDetailsTest() {
        String token = "testToken";
        Token tokenEntity = new Token();
        User user = new User();
        user.setUsername("testUsername");
        tokenEntity.setUser(user);
        tokenEntity.setToken(token);

        when(tokenRepository.findById("testToken")).thenReturn(Optional.of(tokenEntity));

        UserDetails userDetails = authService.getUserDetails(token, false);

        assertNotNull(userDetails);
        assertEquals("testUsername", userDetails.getUsername());
    }

    @Test
    void generateRandomTokenTest() {
        String token = authService.generateRandomToken();

        assertNotNull(token);
        assertEquals(32, token.length()); // UUID'nin uzunluğu 32 karakter olmalıdır
    }

    @Test
    void clearTokenTest() {
        String token = "testToken";
        Token tokenEntity = new Token();
        User user = new User();
        user.setUsername("testUsername");
        tokenEntity.setUser(user);
        tokenEntity.setToken(token);

        when(tokenRepository.findById("testToken")).thenReturn(Optional.of(tokenEntity));

        authService.clearToken(token);

        verify(tokenRepository, times(1)).deleteById("testToken");
    }
}
