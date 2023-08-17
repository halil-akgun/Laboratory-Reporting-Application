package com.ozgur.laboratoryreportingapplication.shared.annotation;

import com.ozgur.laboratoryreportingapplication.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileNumberSizeValidatorForUpdate implements ConstraintValidator<FileNumberSizeForUpdate, String> {

    @Autowired
    private ReportRepository reportRepository;

    private int expectedSize;

    @Override
    public void initialize(FileNumberSizeForUpdate constraintAnnotation) {
        this.expectedSize = constraintAnnotation.expectedSize();
    }

    @Override
    public boolean isValid(String fileNumberWithId, ConstraintValidatorContext context) {

        if (fileNumberWithId == null) return false;

        String fileNumber = fileNumberWithId.split("-")[0];

        return fileNumber.length() == expectedSize;
    }
}
