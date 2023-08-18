package com.ozgur.laboratoryreportingapplication.auth;

import com.ozgur.laboratoryreportingapplication.shared.LoginResponse;
import lombok.Data;

@Data
public class AuthResponse {
	
	private String token;
	
	private LoginResponse user;

}
