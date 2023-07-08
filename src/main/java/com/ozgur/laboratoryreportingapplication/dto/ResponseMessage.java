package com.ozgur.laboratoryreportingapplication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL) // Null fields will not be sent to the client.
public class ResponseMessage<E> {

    private E object;

    private String message;

    private HttpStatus httpStatus;

}
