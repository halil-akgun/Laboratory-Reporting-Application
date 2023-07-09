package com.ozgur.laboratoryreportingapplication.dto.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { UniqueHospitalIdNumberValidator.class })
@Target({  FIELD })
@Retention(RUNTIME)
public @interface UniqueHospitalIdNumber {

    String message() default "Already registered with this Hospital Id Number.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
