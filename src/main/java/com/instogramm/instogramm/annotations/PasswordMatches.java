package com.instogramm.instogramm.annotations;

import com.instogramm.instogramm.validators.PasswordMatchesValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "Password don`t match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
