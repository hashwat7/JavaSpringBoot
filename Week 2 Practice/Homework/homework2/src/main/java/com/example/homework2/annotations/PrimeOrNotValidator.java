package com.example.homework2.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeOrNotValidator implements ConstraintValidator<PrimeOrNotValidation,Long> {

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        long sqrt = (long) Math.sqrt(aLong);
        for(long i = 2; i <sqrt;i++){
            if(aLong%i==0)
                return false;
        }
        return true;
    }
}
