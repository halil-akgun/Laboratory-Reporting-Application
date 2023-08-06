package com.ozgur.laboratoryreportingapplication.shared;

import com.ozgur.laboratoryreportingapplication.shared.annotation.FileType;
import com.ozgur.laboratoryreportingapplication.shared.annotation.UniqueFileNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ReportSaveUpdateRequest {

    @NotBlank
    @UniqueFileNumber
    @Pattern(regexp = "\\d+", message = "{validation.constraints.Pattern.fileNumber.message}")
    @Size(min = 2, max = 15, message = "{validation.constraints.Size.general.message}")
    private String fileNumber;

    @NotBlank
    @Size(min = 2, max = 25, message = "{validation.constraints.Size.general.message}")
    private String patientName;

    @NotBlank
    @Size(min = 2, max = 25, message = "{validation.constraints.Size.general.message}")
    private String patientSurname;

    @NotBlank
    @Pattern(regexp = "\\d+", message = "{validation.constraints.Pattern.patientIdNumber.message}")
    @Size(min = 11, max = 11, message = "{validation.constraints.Size.patientIdNumber.message}")
    private String patientIdNumber;

    @NotBlank
    @Size(min = 2, max = 25, message = "{validation.constraints.Size.general.message}")
    private String diagnosisTitle;

    @NotBlank
    @Size(min = 10, max = 1000, message = "{validation.constraints.Size.general.message}")
    private String diagnosisDetails;

    @NotNull
    @PastOrPresent(message = "{validation.constraints.Date.dateOfReport.message}")
    private Date dateOfReport;

    @FileType(types = {"jpg", "jpeg", "png"})
    private String imageOfReport;

}
