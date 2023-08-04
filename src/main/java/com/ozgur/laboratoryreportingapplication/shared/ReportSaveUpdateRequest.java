package com.ozgur.laboratoryreportingapplication.shared;

import com.ozgur.laboratoryreportingapplication.shared.annotation.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ReportSaveUpdateRequest {

    @Pattern(regexp = "\\d+", message = "{validation.constraints.Pattern.fileNumber.message}")
    @Size(min = 2, max = 15, message = "{validation.constraints.Size.general.message}")
    private String fileNumber;

    @Size(min = 2, max = 25, message = "{validation.constraints.Size.general.message}")
    private String patientName;

    @Size(min = 2, max = 25, message = "{validation.constraints.Size.general.message}")
    private String patientSurname;

    @Pattern(regexp = "\\d+", message = "{validation.constraints.Pattern.patientIdNumber.message}")
    @Size(min = 11, max = 11, message = "{validation.constraints.Size.patientIdNumber.message}")
    private String patientIdNumber;

    @Size(min = 2, max = 25, message = "{validation.constraints.Size.general.message}")
    private String diagnosisTitle;

    @Size(min = 10, max = 500, message = "{validation.constraints.Size.general.message}")
    private String diagnosisDetails;

    @PastOrPresent(message = "{validation.constraints.Date.dateOfReport.message}")
    private Date dateOfReport;

    @FileType(types = {"jpg", "jpeg", "png"})
    private String imageOfReport;

}
