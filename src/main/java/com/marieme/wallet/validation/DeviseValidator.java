package com.marieme.wallet.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class DeviseValidator implements ConstraintValidator<ValidDevise, String> {

    private static final Set<String> VALID_DEVISES = Set.of("XOF", "EUR", "USD", "GBP", "CAD", "CHF", "JPY", "CNY");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // @NotNull handles null
        }
        return VALID_DEVISES.contains(value.toUpperCase());
    }
}
