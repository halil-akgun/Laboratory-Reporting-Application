package com.ozgur.laboratoryreportingapplication.shared.annotation;

import com.ozgur.laboratoryreportingapplication.entity.Report;
import com.ozgur.laboratoryreportingapplication.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Optional;

public class UniqueFileNumberValidatorForUpdate implements ConstraintValidator<UniqueFileNumberForUpdate, String> {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public boolean isValid(String fileNumberWithId, ConstraintValidatorContext context) {

        if (fileNumberWithId == null) return true;

        String fileNumber = fileNumberWithId.split("-")[0];
        Long id = Long.parseLong(fileNumberWithId.split("-")[1]);

        Report reportWithId = reportRepository.findById(id).orElse(null);
        if (reportWithId != null && Objects.equals(reportWithId.getFileNumber(), fileNumber)) return true;

        Optional<Report> reportWithFileNumber = reportRepository.findByFileNumber(fileNumber);

        return reportWithFileNumber.isEmpty();
    }
}
