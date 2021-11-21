package com.instogramm.instogramm.validators;

import com.instogramm.instogramm.annotations.PasswordMatches;
import com.instogramm.instogramm.payload.request.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {}

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SignUpRequest signUpRequest = (SignUpRequest) o;
        return signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword());
    }
}
