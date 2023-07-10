package com.ozgur.laboratoryreportingapplication.dto.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { NotAdminValidator.class })
@Target({  FIELD })
@Retention(RUNTIME)
public @interface NotAdmin {

    String message() default "{validation.constraints.username.NotAdmin.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
