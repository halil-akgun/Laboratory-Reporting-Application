package com.ozgur.laboratoryreportingapplication.shared.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotAdminValidator implements ConstraintValidator<NotAdmin, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if (username == null) return true;

        return !username.equalsIgnoreCase("admin");

    }

}
