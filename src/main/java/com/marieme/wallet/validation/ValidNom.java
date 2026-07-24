package com.marieme.wallet.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NomValidator.class)
@Documented
public @interface ValidNom {

    String message() default "Le nom ne doit pas contenir de caracteres speciaux ou de chiffres";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
