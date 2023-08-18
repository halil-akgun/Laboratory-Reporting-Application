package com.ozgur.laboratoryreportingapplication.auth;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.ozgur.laboratoryreportingapplication.configuration.UserDetailsImpl;
import com.ozgur.laboratoryreportingapplication.controller.UserController;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import com.ozgur.laboratoryreportingapplication.shared.LoginResponse;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
		if(user == null) {
			throw new AuthException();			
		}
		boolean matches = passwordEncoder.matches(credentials.getPassword(), user.getPassword());
		if(!matches) {
			throw new AuthException();
		}
		LoginResponse loginResponse = Mapper.loginResponseFromUser(user);
		String token = generateRandomToken();
		
		Token tokenEntity = new Token();
		tokenEntity.setToken(token);
		tokenEntity.setUser(user);
		tokenRepository.save(tokenEntity);
		AuthResponse response = new AuthResponse();
		response.setUser(loginResponse);
		response.setToken(token);

		logger.info("User logged in with username " + loginResponse.getUsername());

		return response;
	}

	@Transactional
	public UserDetails getUserDetails(String token) {
		Optional<Token> optionalToken = tokenRepository.findById(token);
        return optionalToken.map(value -> new UserDetailsImpl(value.getUser())).orElse(null);
    }
	
	public String generateRandomToken() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public void clearToken(String token) {
		tokenRepository.deleteById(token);
		
	}

}
