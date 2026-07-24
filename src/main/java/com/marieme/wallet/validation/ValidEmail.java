package com.marieme.wallet.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {

    String message() default "L'email doit avoir un format valide et contenir un domaine (ex: @example.com)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
