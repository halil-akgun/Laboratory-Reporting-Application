package com.ozgur.laboratoryreportingapplication.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ozgur.laboratoryreportingapplication.shared.annotation.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false, unique = true)
    private String fileNumber;

    @Column(length = 25, nullable = false)
    private String patientName;

    @Column(length = 25, nullable = false)
    private String patientSurname;

    @Column(length = 11, nullable = false)
    private String patientIdNumber;

    @Column(length = 25, nullable = false)
    private String diagnosisTitle;

    @Column(length = 1000, nullable = false)
    private String diagnosisDetails;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateOfReport;

    private String imageOfReport;

    @ManyToOne
    private User user;

    private Date timestamp;
}
