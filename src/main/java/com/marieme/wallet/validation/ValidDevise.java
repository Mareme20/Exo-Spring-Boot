package com.marieme.wallet.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DeviseValidator.class)
@Documented
public @interface ValidDevise {

    String message() default "Devise invalide. Les devises acceptees sont : XOF, EUR, USD, GBP, CAD";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
