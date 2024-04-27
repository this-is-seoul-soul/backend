package com.seouldata.fest.domain.fest.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCodeNameValidator.class)
@Documented
public @interface ValidCodeName {
    String message() default "Invalid code name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

