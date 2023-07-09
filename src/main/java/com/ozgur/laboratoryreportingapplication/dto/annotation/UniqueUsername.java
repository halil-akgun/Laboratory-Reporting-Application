package com.ozgur.laboratoryreportingapplication.dto.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { UniqueUsernameValidator.class })
@Target({  FIELD })
@Retention(RUNTIME)
public @interface UniqueUsername {

    String message() default "Already registered with this username.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
