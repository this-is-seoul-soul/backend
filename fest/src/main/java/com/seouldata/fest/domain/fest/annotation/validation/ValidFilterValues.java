package com.seouldata.fest.domain.fest.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidFilterValuesValidator.class})
@Documented
public @interface ValidFilterValues {
    String message() default "Invalid filter value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
