package com.ozgur.laboratoryreportingapplication.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponse {

    private Long id;

    private String fileNumber;

    private String patientName;

    private String patientSurname;

    private String patientIdNumber;

    private String diagnosisTitle;

    private String diagnosisDetails;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfReport;

    private String imageOfReport;

    private Date timestamp;

    private String laborantNameSurname;

}
