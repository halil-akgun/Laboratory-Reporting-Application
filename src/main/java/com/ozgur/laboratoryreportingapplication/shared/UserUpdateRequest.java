package com.ozgur.laboratoryreportingapplication.shared;

import com.ozgur.laboratoryreportingapplication.shared.annotation.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserUpdateRequest {

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

    @NotBlank(message = "{validation.constraints.NotBlank.hospitalIdNumber.message}")
    @Pattern(regexp = "\\d+", message = "{validation.constraints.Pattern.hospitalIdNumber.message}")
    @Size(min = 7, max = 7, message = "{validation.constraints.Size.hospitalIdNumber.message}")
    private String hospitalIdNumber;

    @FileType(types = {"jpg", "jpeg", "png"})
    private String image;
}
