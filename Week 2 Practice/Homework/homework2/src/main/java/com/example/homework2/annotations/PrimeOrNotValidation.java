package com.example.homework2.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = {PrimeOrNotValidator.class})
public @interface  PrimeOrNotValidation {
    String message() default "The provided number should be a prime number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
