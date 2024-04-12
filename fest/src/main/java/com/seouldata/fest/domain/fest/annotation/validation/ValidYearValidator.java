package com.seouldata.fest.domain.fest.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;
import java.util.List;

public class ValidYearValidator implements ConstraintValidator<ValidYear, List<Integer>> {

    @Override
    public void initialize(ValidYear constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<Integer> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int currentYear = Year.now().getValue();

        for(int year : value) {
            if(year < 2023 || year > currentYear) {
                return false;
            }
        }

        return true;
    }
}
