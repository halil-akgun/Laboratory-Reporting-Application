package com.ozgur.laboratoryreportingapplication.dto.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

public class NotAdminValidator implements ConstraintValidator<NotAdmin, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        return !username.equalsIgnoreCase("admin");

    }

}
