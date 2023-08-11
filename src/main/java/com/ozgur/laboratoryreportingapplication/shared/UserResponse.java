package com.ozgur.laboratoryreportingapplication.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private String username;

    private String name;

    private String surname;

    private String hospitalIdNumber;

    private String image;

    private String fullName;

}
