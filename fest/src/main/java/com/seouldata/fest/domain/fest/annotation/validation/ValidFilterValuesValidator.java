package com.seouldata.fest.domain.fest.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidFilterValuesValidator implements ConstraintValidator<ValidFilterValues, List<String>> {

    private static final Set<String> VALID_FILTER_VALUES = new HashSet<>();

    static {
        VALID_FILTER_VALUES.add("year");
        VALID_FILTER_VALUES.add("codename");
    }

    @Override
    public void initialize(ValidFilterValues constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {

        if(value == null || value.isEmpty()) {
            return true;
        }

        for (String val : value) {
            if (!VALID_FILTER_VALUES.contains(val.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
