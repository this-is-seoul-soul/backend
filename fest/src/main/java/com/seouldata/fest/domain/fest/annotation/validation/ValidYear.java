package com.seouldata.fest.domain.fest.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidYearValidator.class)
@Documented
public @interface ValidYear {
    String message() default "Year should be between 2023 and current year";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

