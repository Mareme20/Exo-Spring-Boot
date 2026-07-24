package com.marieme.wallet.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MontantValidator.class)
@Documented
public @interface ValidMontant {

    String message() default "Le montant doit etre positif et ne peut pas depasser 10 chiffres avant la virgule";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
