package com.ozgur.laboratoryreportingapplication.shared.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { FileNumberPatternValidatorForUpdate.class })
@Target({  FIELD })
@Retention(RUNTIME)
public @interface FileNumberPatternForUpdate {

    String message() default "{validation.constraints.Pattern.fileNumber.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
