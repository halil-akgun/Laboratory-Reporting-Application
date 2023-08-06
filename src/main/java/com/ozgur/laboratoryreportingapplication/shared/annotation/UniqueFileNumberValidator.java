package com.ozgur.laboratoryreportingapplication.shared.annotation;

import com.ozgur.laboratoryreportingapplication.entity.Report;
import com.ozgur.laboratoryreportingapplication.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueFileNumberValidator implements ConstraintValidator<UniqueFileNumber, String> {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public boolean isValid(String fileNumber, ConstraintValidatorContext context) {

        if (fileNumber == null) return true;

        Optional<Report> report = reportRepository.findByFileNumber(fileNumber);

        return report.isEmpty();
    }
}
