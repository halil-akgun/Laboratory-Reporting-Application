package com.ozgur.laboratoryreportingapplication.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegisterRequest {

    @NotBlank
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your username must consist of the characters.")
    @Size(min = 2, max = 20, message = "Username '${validatedValue}' must be between {min} and {max} chars long")
    private String username;
	
    @NotBlank
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must consist of the characters.")
    @Size(min = 2, max = 20, message = "Name '${validatedValue}' must be between {min} and {max} chars long")
    private String name;
    
    @NotBlank
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your surname must consist of the characters.")
    @Size(min = 2, max = 20, message = "Surname '${validatedValue}' must be between {min} and {max} chars long")
	private String surname;
    
    @NotBlank
    @Pattern(regexp = "\\d+", message = "Hospital ID Number must consist of digits only.")
    @Size(min = 7, max = 7, message = "Hospital ID Number '${validatedValue}' must be {max} chars long")
	private String hospitalIdNumber;
    
    @NotBlank
    @Size(min = 8, max = 30, message = "Password '${validatedValue}' must be between {min} and {max} chars long")
    private String password;
    
    private String role;

}
