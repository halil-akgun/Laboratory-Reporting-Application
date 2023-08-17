package com.ozgur.laboratoryreportingapplication.shared.annotation;

import com.ozgur.laboratoryreportingapplication.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileNumberPatternValidatorForUpdate implements ConstraintValidator<FileNumberPatternForUpdate, String> {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public boolean isValid(String fileNumberWithId, ConstraintValidatorContext context) {

        if (fileNumberWithId == null) return true;

        String fileNumber = fileNumberWithId.split("-")[0];
        String fileNumberDigits = fileNumber.replaceAll("[^0-9]", "");

        return fileNumberDigits.length() == fileNumber.length();
    }
}
