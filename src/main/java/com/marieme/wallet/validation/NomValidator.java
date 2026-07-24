package com.marieme.wallet.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NomValidator implements ConstraintValidator<ValidNom, String> {

    private static final Pattern NOM_PATTERN =
            Pattern.compile("^[a-zA-Z\\s\\-']+$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // @NotBlank handles null/blank
        }
        return NOM_PATTERN.matcher(value).matches();
    }
}
