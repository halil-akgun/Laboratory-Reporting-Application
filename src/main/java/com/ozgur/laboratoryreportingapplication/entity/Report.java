package com.ozgur.laboratoryreportingapplication.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 9, nullable = false, unique = true)
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
    private LocalDate dateOfReport;

    private String imageOfReport;

    @ManyToOne
    private User user;

    private LocalDate timestamp;
}
