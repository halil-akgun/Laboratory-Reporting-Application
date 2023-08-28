package com.ozgur.laboratoryreportingapplication.auth;

import java.time.LocalDateTime;
import java.util.*;

import javax.transaction.Transactional;

import com.ozgur.laboratoryreportingapplication.configuration.UserDetailsImpl;
import com.ozgur.laboratoryreportingapplication.entity.Token;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.repository.TokenRepository;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import com.ozgur.laboratoryreportingapplication.shared.LoginResponse;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    TokenRepository tokenRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    Logger logger = LoggerFactory.getLogger(AuthService.class);


    public AuthResponse authenticate(Credentials credentials) {
        User user = userRepository.findByUsername(credentials.getUsername()).orElse(null);
        if (user == null) {
            throw new AuthException();
        }
        boolean matches = passwordEncoder.matches(credentials.getPassword(), user.getPassword());
        if (!matches) {
            throw new AuthException();
        }
        LoginResponse loginResponse = Mapper.loginResponseFromUser(user);
        String token = generateRandomToken();

        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setUser(user);
        tokenEntity.setLastActionTime(LocalDateTime.now());
        tokenRepository.save(tokenEntity);
        AuthResponse response = new AuthResponse();
        response.setUser(loginResponse);
        response.setToken(token);

        logger.info("User logged in with username " + loginResponse.getUsername());

        return response;
    }

    @Transactional
    public UserDetails getUserDetails(String token, boolean sessionValidityCheck) {
        Optional<Token> optionalToken = tokenRepository.findById(token);
        if (optionalToken.isPresent() && !sessionValidityCheck) {
            optionalToken.get().setLastActionTime(LocalDateTime.now());
        }
        return optionalToken.map(value -> new UserDetailsImpl(value.getUser())).orElse(null);
    }

    public String generateRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void clearToken(String token) {
        Token tokenInDb = tokenRepository.findById(token).orElse(null);
        assert tokenInDb != null;
        logger.info("User logged out with username " + tokenInDb.getUser().getUsername());
        tokenRepository.deleteById(token);
    }


    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void cleanupExpiredTokens() {
        LocalDateTime eightHoursAgo = LocalDateTime.now().minusHours(8);

        List<Token> expiredTokens = tokenRepository.findByLastActionTimeBefore(eightHoursAgo);

        tokenRepository.deleteAll(expiredTokens);

        logger.info(expiredTokens.size() + " expired tokens have been cleaned up.");
    }

    public Map<String, Boolean> sessionValidityCheck(String token) {
        boolean isExist = tokenRepository.existsById(token);
        Map<String, Boolean> result = new HashMap<>();
        result.put("isSessionActive", isExist);
        return result;
    }

    public Map<String, Boolean> checkSessionOnAnotherDevice(Credentials credentials) {
        User user = userRepository.findByUsername(credentials.getUsername()).orElse(null);
        if (user == null) {
            throw new AuthException();
        }
        boolean matches = passwordEncoder.matches(credentials.getPassword(), user.getPassword());
        if (!matches) {
            throw new AuthException();
        }

        List<Token> userTokens = tokenRepository.findByUser(user);

        Map<String, Boolean> result = new HashMap<>();
        result.put("isThereAnotherSession", !userTokens.isEmpty());
        return result;
    }

    public void closeOtherSessions(Credentials credentials) {
        User user = userRepository.findByUsername(credentials.getUsername()).orElse(null);
        if (user == null) {
            throw new AuthException();
        }
        boolean matches = passwordEncoder.matches(credentials.getPassword(), user.getPassword());
        if (!matches) {
            throw new AuthException();
        }

        List<Token> userTokens = tokenRepository.findByUser(user);

        tokenRepository.deleteAll(userTokens);
    }
}
