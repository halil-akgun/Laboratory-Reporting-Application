package com.ozgur.laboratoryreportingapplication.shared;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ozgur.laboratoryreportingapplication.shared.annotation.NotAdmin;
import com.ozgur.laboratoryreportingapplication.shared.annotation.UniqueHospitalIdNumber;
import com.ozgur.laboratoryreportingapplication.shared.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegisterRequest {

/*
    UniqueUsername and UniqueHospitalIdNumber checks could not be made when a request did not pass
    other validations. In this way, the user will not have to correct the form for the 2nd time.

    For example: surname=null and the entered username exists in the db. In this case, if there is no
    UniqueUsername annotation, only the error message about the username will be displayed.
*/

    @UniqueUsername
    @NotAdmin
    @NotBlank(message = "{validation.constraints.NotBlank.username.message}")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "{validation.constraints.Pattern.username.message}")
    @Size(min = 2, max = 20, message = "{validation.constraints.Size.username.message}")
    private String username;

    @NotBlank(message = "{validation.constraints.NotBlank.name.message}")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "{validation.constraints.Pattern.name.message}")
    @Size(min = 2, max = 20, message = "{validation.constraints.Size.name.message}")
    private String name;

    @NotBlank(message = "{validation.constraints.NotBlank.surname.message}")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "{validation.constraints.Pattern.surname.message}")
    @Size(min = 2, max = 20, message = "{validation.constraints.Size.surname.message}")
    private String surname;

    @UniqueHospitalIdNumber
    @NotBlank(message = "{validation.constraints.NotBlank.hospitalIdNumber.message}")
    @Pattern(regexp = "\\d+", message = "{validation.constraints.Pattern.hospitalIdNumber.message}")
    @Size(min = 7, max = 7, message = "{validation.constraints.Size.hospitalIdNumber.message}")
    private String hospitalIdNumber;

    @NotBlank(message = "{validation.constraints.NotBlank.password.message}")
    @Size(min = 8, max = 30, message = "{validation.constraints.Size.password.message}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{validation.constraints.Pattern.password.message}")
    private String password;

}
