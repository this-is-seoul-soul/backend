package com.seouldata.fest.domain.fest.annotation.validation;

import com.seouldata.fest.domain.fest.entity.Codename;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidCodeNameValidator implements ConstraintValidator<ValidCodeName, List<String>> {

    @Override
    public void initialize(ValidCodeName constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // null 또는 빈 목록은 유효하다고 가정합니다.
        }

        for (String codeName : value) {
            boolean isValidCodeName = false;
            for (Codename codename : Codename.values()) {
                if (codename.getCodeType().equals(codeName)) {
                    isValidCodeName = true;
                    break;
                }
            }
            if (!isValidCodeName) {
                return false;
            }
        }
        return true;
    }
}

