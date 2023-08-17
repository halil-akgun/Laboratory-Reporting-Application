package com.ozgur.laboratoryreportingapplication.shared;

import com.ozgur.laboratoryreportingapplication.shared.annotation.FileNumberPatternForUpdate;
import com.ozgur.laboratoryreportingapplication.shared.annotation.FileNumberSizeForUpdate;
import com.ozgur.laboratoryreportingapplication.shared.annotation.FileType;
import com.ozgur.laboratoryreportingapplication.shared.annotation.UniqueFileNumberForUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ReportUpdateRequest {

    @NotBlank
    @UniqueFileNumberForUpdate
    @FileNumberPatternForUpdate
    @FileNumberSizeForUpdate(expectedSize = 9)
    private String fileNumberWithId;

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
    @Size(min = 2, max = 40, message = "{validation.constraints.Size.general.message}")
    private String diagnosisTitle;

    @NotBlank
    @Size(min = 10, max = 1000, message = "{validation.constraints.Size.general.message}")
    private String diagnosisDetails;

    @NotNull
    @PastOrPresent(message = "{validation.constraints.Date.dateOfReport.message}")
    private LocalDate dateOfReport;

    @FileType(types = {"jpg", "jpeg", "png"})
    private String imageOfReport;

}
