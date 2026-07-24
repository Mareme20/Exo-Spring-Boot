package com.marieme.wallet.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class MontantValidator implements ConstraintValidator<ValidMontant, BigDecimal> {

    private static final BigDecimal MAX_VALUE = new BigDecimal("9999999999.9999");

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotNull handles null
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        return value.compareTo(MAX_VALUE) <= 0;
    }
}
